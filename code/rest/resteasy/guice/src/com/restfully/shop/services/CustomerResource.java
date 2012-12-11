package com.restfully.shop.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.jboss.resteasy.examples.library.data.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.restfully.shop.domain.Customer;

@Path("/customers")
public class CustomerResource {
	private Map<Integer, Customer> customerDB = new ConcurrentHashMap<Integer, Customer>();
	private AtomicInteger idCounter = new AtomicInteger();
	
	//这里使用了ehcache
	// 使用默认配置文件创建CacheManager
	CacheManager manager = CacheManager.create();
	// 通过manager可以生成指定名称的Cache对象
	Cache cache = manager.getCache("demoCache");

	public CustomerResource() {
		//加载模块
		customerDB.put(1, new Customer(1, "Mr", "xie", "天通苑", "北京", "1", "10000","中国"));
	}

	@POST
	@Consumes("application/xml")
	public Response createCustomer(InputStream is) {
		Customer customer = readCustomer(is);
		customer.setId(idCounter.incrementAndGet());
		customerDB.put(customer.getId(), customer);
		System.out.println("Created customer " + customer.getId());
		return Response.created(URI.create("/customers/" + customer.getId()))
				.build();

	}

	@GET
	@Path("{id}")
	@Produces("application/xml")
	public StreamingOutput getCustomer(@PathParam("id") int id) {
		System.out.println("-------@GET-------");
		final Customer customer = customerDB.get(id);
		if (customer == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException,
					WebApplicationException {
				outputCustomer(outputStream, customer);
			}
		};
	}
	
	@GET
	@Path("demo")
	public Book demo(@QueryParam("name") String name,@HeaderParam("Accept-Charset") String charset,@HeaderParam("Cookie") String cookie,@HeaderParam("User-Agent") String agent){
		
//		System.out.println(name);
		System.out.println(charset);
		System.out.println(cookie);
		System.out.println(agent);
		
		//CacheConfiguration config = cache.getCacheConfiguration();
		// 使用manager移除指定名称的Cache对象
		//manager.removeCache("demoCache");
		net.sf.ehcache.Element element = new net.sf.ehcache.Element("demokey", "test ehcache!");
		if(cache.get("demokey") == null){
			cache.put(element);
			System.out.println("缓存为空");
		}else{
			System.out.println("缓存不为空");
		}
		return new Book("Hartwell", "9857-ISBN-65895", "Thinking in programming");
	}


	@PUT
	@Path("{id}")
	@Consumes("application/xml")
	public void updateCustomer(@PathParam("id") int id, InputStream is) {
		System.out.println("-------@PUT-------");
		Customer update = readCustomer(is);
		Customer current = customerDB.get(id);
		if (current == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		current.setFirstName(update.getFirstName());
		current.setLastName(update.getLastName());
		current.setStreet(update.getStreet());
		current.setState(update.getState());
		current.setZip(update.getZip());
		current.setCountry(update.getCountry());
	}

	protected void outputCustomer(OutputStream os, Customer cust)
			throws IOException {
		PrintStream writer = new PrintStream(os);
		writer.println("<customer id=\"" + cust.getId() + "\">");
		writer.println("   <first-name>" + cust.getFirstName()
				+ "</first-name>");
		writer.println("   <last-name>" + cust.getLastName() + "</last-name>");
		writer.println("   <street>" + cust.getStreet() + "</street>");
		writer.println("   <city>" + cust.getCity() + "</city>");
		writer.println("   <state>" + cust.getState() + "</state>");
		writer.println("   <zip>" + cust.getZip() + "</zip>");
		writer.println("   <country>" + cust.getCountry() + "</country>");
		writer.println("</customer>");
	}

	protected Customer readCustomer(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			Customer cust = new Customer();
			if (root.getAttribute("id") != null
					&& !root.getAttribute("id").trim().equals(""))
				cust.setId(Integer.valueOf(root.getAttribute("id")));
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (element.getTagName().equals("first-name")) {
					cust.setFirstName(element.getTextContent());
				} else if (element.getTagName().equals("last-name")) {
					cust.setLastName(element.getTextContent());
				} else if (element.getTagName().equals("street")) {
					cust.setStreet(element.getTextContent());
				} else if (element.getTagName().equals("city")) {
					cust.setCity(element.getTextContent());
				} else if (element.getTagName().equals("state")) {
					cust.setState(element.getTextContent());
				} else if (element.getTagName().equals("zip")) {
					cust.setZip(element.getTextContent());
				} else if (element.getTagName().equals("country")) {
					cust.setCountry(element.getTextContent());
				}
			}
			return cust;
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}

}
