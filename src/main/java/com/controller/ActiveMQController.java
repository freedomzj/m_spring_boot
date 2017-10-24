package com.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.Explore;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Created by zengjie on 17/6/23.
 */
@Controller
@RequestMapping("active")
public class ActiveMQController {

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration freemarkerConfig;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Queue queue;

	@RequestMapping("index")
	public String index() {
		return "activeMQAjax";
	}

	@RequestMapping("amq")
	public String amq() {
		return "a";
	}

	

	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(30);// 开启30个线程同时发送
		String msg = "safd";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String star = sf.format(new Date());
		for (int i = 0; i < 2000; i++) {
			executor.execute(new Runnable() {
				public void run() {
					System.out.println(sf.format(new Date()) + msg + "  Callable hashCode：" + this.hashCode());
				}
			});
		}

		executor.shutdown();
		System.out.println("开始时间" + star);
		System.out.println("结束时间" + sf.format(new Date()));
	}

	private AtomicInteger atomicInteger=new AtomicInteger(0);//初始为0
	
	@RequestMapping(value = "send/{msg}", method = RequestMethod.GET)
	@ResponseBody
	public String sendMessage(@PathVariable String msg) {
		// 测试 MQ 并发
		// 1.同时发送2000条消息 开启监听回调 MongoDB查询 耗时未算
		// 2.MongoDB 数据key 智齿探索器(1-500万) 
		// 3.封装查询key 为智齿探索器(1-2000)
		// 4.前端ajax轮询 每5秒调用一次
		// 5.对查询的结果进行json文本存储在服务器
		// 6.前端需要结果对文本进行读取 
		//--------------low爆的分割线-------------------
		//1.客户端发送一个查询请求 (消费者 consumers) 
		//2.生成一个队列			(消费者开始需求 consumers do work )
		//3.客户关注这个队列 		(内容提供者mongeDb supplier )
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);// 开启20个线程同时发送
		for (int i = 0; i < 2000; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					safeCount();//先++
					System.out.println(atomicInteger.get());
					jmsMessagingTemplate.convertAndSend(queue, "智齿探索器"+atomicInteger.get());
				}
			});
		}
		executor.shutdown();
		return "成功发送" + msg;
	}
	
	
	private void safeCount(){
		for(;;){
			int i=atomicInteger.get();
			boolean suc=atomicInteger.compareAndSet(i, ++i);
			if(suc){
				break;
			}
		}
	}
	
	/**
	 * 
	 * JMS 队列的监听容器工厂
	 */
	@Bean(name = "booktaskFactory")
	public JmsListenerContainerFactory<?> queueListenerFactory(ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setPubSubDomain(false);
		factory.setConnectionFactory(connectionFactory);
		// 设置连接数
		factory.setConcurrency("10-20");
		// 重连间隔时间
		factory.setRecoveryInterval(1000L);
		
		factory.setTaskExecutor(Executors.newFixedThreadPool(20));  
		return factory;
	}

	/**
	 * 监听 book:querytask队列
	 * 
	 * @param msg
	 */
	@JmsListener(destination = "book:querytask",containerFactory="booktaskFactory")
	@Transactional
	public void create(String msg) {
		// 模拟 2000 个用户 同时查询 2000条
		// 1.userId + key
		// 先判断是否有重复的key和用户 然后进行任务调度查询
		// if (!this.processedUIDs.contains(orderDTO.getToken())) {
		// } else {
		// LOG.info("Duplicate jms message:{}", orderDTO);
		// }
		// System.out.println(msg);
//		String key = "智齿探索器0";
		final Query query = new Query();
		Criteria criteria = new Criteria();
		// if(key!=null && key.length()==10){
		criteria.and("explore").is(msg);
		// query.skip(curPage-1);
		// query.limit(pageSize);
		// }else{
		// criteria.and("explore").regex(key);
		// query.limit(1000);
		// }
		// criteria.wshere(key);
		// criteria.and("key").alike(sample)
		query.addCriteria(criteria);
		// query.maxScan(1000);
		List<Explore> list = mongoTemplate.find(query, Explore.class);
		if (list != null && list.size() > 0) {
			Explore explore = list.get(0);
			System.out.println("查询key:"+msg+"-------返回结果:"+explore.getExplore() + "联系方式" + explore.getContact());
		}
	}

	

	@RequestMapping("sendEmail")
	@ResponseBody
	String home() {
		try {
			sendEmail();
			return "Email Sent!";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error in sending email: " + ex;
		}
	}

	private void sendEmail() throws Exception {
		MimeMessage message = sender.createMimeMessage();

		// MimeMessage helper = new MimeMessage(message);

		Map<String, Object> model = new HashMap<>();

		model.put("user", "qpt");

		// set loading location to src/main/resources
		// You may want to use a subfolder such as /templates here
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
		Template t = freemarkerConfig.getTemplate("email.ftl");
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("1063304800@qq.com"));// 写上收件人
		message.setFrom(new InternetAddress("354253175@qq.com"));// 写上发送人，即发送方QQ邮箱
		message.setText(text); // set to html
		message.setSubject("Hi");
		message.setContent(text, "text/html;charset=UTF-8");// 内容
		sender.send(message);
	}
}
