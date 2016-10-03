package junrui.web;

import junrui.data.GraphResult;
import junrui.logic.*;
import junrui.mapper.EntityMapper;
import junrui.mapper.OrderMapper;
import junrui.mapper.PublicationMapper;
import junrui.mapper.UserMapper;
import junrui.model.Entity;
import junrui.model.Graph;
import junrui.model.Order;
import junrui.model.Person;
import junrui.model.PublPerson;
import junrui.model.Publication;
import junrui.model.User;
import junrui.model.Venue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class PageController {

    private static final Logger logger = LoggerFactory.getLogger(PageController.class);
    private static final String ATTR_USER = "user";
    private static final String ATTR_ADMIN = "admin";

    @Autowired
    private SearchLogic searchLogic;

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private OrderLogic orderLogic;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PublicationMapper publicationMapper;
    
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private GraphSearchLogic graphSearchLogic;

    @RequestMapping(value = "/graph", method = RequestMethod.GET)
    public String graph(@RequestParam MultiValueMap<String, String> params, Map<String, Object> model)
    {
    	if (params.containsKey("keyword"))
    	{
            String keyword = params.getFirst("keyword");
            System.out.print(keyword);
            
            List<Graph> resultList = entityMapper.graphSearch(keyword);
            model.put("searchResult", resultList);
            
     		/*for(Graph g : resultList)
     		{
     			if(g.getNodeFrom().startsWith("P"))
     			{
     				entityMapper
     				System.out.println(g.toString());
     			}
     			else
     			{
     				entityMapper
     			}  		
     		}*/
    	}
    	return "graph.jsp";
    } 
    

    public String graph() {
		return "graph.jsp";
    }

    @RequestMapping(value = "/graph", method = RequestMethod.POST)
    @ResponseBody
    public GraphResult graphPost(String keyword, int type) {
        return graphSearchLogic.search(keyword, type);
    }
  
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Map<String, Object> model) {
        List<Integer> ids = publicationMapper.selectAllIds();
        Collections.shuffle(ids);
        ids = ids.subList(0, 10);
        List<Publication> publications = publicationMapper.selectByIds(ids);
        model.put("publications", publications);
        
        return "home.jsp";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register.jsp";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> registerPost(User user) {
        user.setBanned(false);
        boolean ret = userLogic.register(user);
        logger.debug(user.getCreditCard());
        if (ret) {
            return ResponseEntity.ok("register succeeded");
        }
        return ResponseEntity.badRequest().body("username exists");
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String verify(int id, String code, Map<String, Object> model, HttpSession session) {
        if (userLogic.verify(id, code)) {
            User user = userMapper.selectByPrimaryKey(id);
            session.setAttribute(ATTR_USER, user);
            model.put("success", true);
        } else {
            model.put("success", false);
        }
        return "verify.jsp";
    }

    @RequestMapping("/testsession")
    @ResponseBody
    public String testSession(HttpSession session) {
        User user = userMapper.selectByPrimaryKey(1);
        session.setAttribute(ATTR_USER, user);
        return session.getServletContext().getRealPath("/static/");
    }

    @RequestMapping("/testpost")
    public ResponseEntity<String> testPost(User user) {
        logger.debug(user.getCreditCard());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("asdasdasd");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(String username, String password, HttpSession session) {
        User user = userMapper.selectByUsername(username);
        if (user == null || user.getType() == 2) {
            return ResponseEntity.badRequest().body("username doesn't exist");
        } else if (user.getPassword() == null) {
            return ResponseEntity.badRequest().body("user's is not verified");
        } else if (!user.getPassword().equals(password)) {
            return ResponseEntity.badRequest().body("wrong password");
        } else if (user.getBanned()) {
            return ResponseEntity.badRequest().body("user is banned");
        }
        session.setAttribute(ATTR_USER, user);
        return ResponseEntity.ok("login succeeded");
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/home";
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String password(HttpSession session) {
        User sessionUser = (User) session.getAttribute(ATTR_USER);
        if (sessionUser == null) {
            return "redirect:/home";
        }
        return "password.jsp";
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ResponseEntity<String> passwordPost(String password, String oldPassword, HttpSession session) {
        User sessionUser = (User) session.getAttribute(ATTR_USER);
        if (sessionUser == null) {
            return ResponseEntity.badRequest().body("not login");
        }
        int userId = sessionUser.getId();
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getPassword() != null && !user.getPassword().equals(oldPassword)) {
            return ResponseEntity.badRequest().body("wrong password");
        }
        user.setPassword(password);
        userMapper.updateByPrimaryKey(user);
        return ResponseEntity.ok("password updated");
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(HttpSession session) {
        User sessionUser = (User) session.getAttribute(ATTR_USER);
        if (sessionUser == null) {
            return "redirect:/home";
        }
        return "profile.jsp";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity<String> profilePost(User user, HttpSession session) {
        User sessionUser = (User) session.getAttribute(ATTR_USER);
        if (sessionUser == null) {
            return ResponseEntity.badRequest().body("not login");
        }
        user.setId(sessionUser.getId());
        user.setBanned(sessionUser.getBanned());
        userMapper.updateByPrimaryKey(user);
        user = userMapper.selectByPrimaryKey(sessionUser.getId());
        session.setAttribute(ATTR_USER, user);
        return ResponseEntity.ok("profile updated");
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart(Map<String, Object> model, HttpSession session) {
        User sessionUser = (User) session.getAttribute(ATTR_USER);
        if (sessionUser == null || sessionUser.getType() != 0) {
            return "redirect:/home";
        }
        model.put("orders", orderMapper.selectByUserId(sessionUser.getId(), false));
        return "cart.jsp";
    }

    @RequestMapping(value = "/cart/{action}", method = RequestMethod.POST)
    public ResponseEntity<String> cartAdd(@PathVariable String action, int id, HttpSession session) {
        User sessionUser = (User) session.getAttribute(ATTR_USER);
        if (sessionUser == null) {
            return ResponseEntity.badRequest().body("not login");
        }
        if (sessionUser.getType() != 0) {
            return ResponseEntity.badRequest().body("not a customer");
        }
        if ("add".equals(action)) {
            int userId = sessionUser.getId();
            orderLogic.cartAdd(userId, id);
        } else if ("increment".equals(action)) {
            orderMapper.incrementById(id);
        } else if ("decrement".equals(action)) {
            orderMapper.decrementById(id);
        } else if ("remove".equals(action)) {
            orderMapper.updateRemoveTs(id);
        } else if ("commit".equals(action)) {
            orderLogic.cartCommit(sessionUser);
        }
        return ResponseEntity.ok("added to cart");
    }

    @RequestMapping(value = "/seller", method = RequestMethod.GET)
    public String seller(String action, Integer page, Map<String, Object> model, HttpSession session) {
        User sessionUser = (User) session.getAttribute(ATTR_USER);
        if (sessionUser == null || sessionUser.getType() != 1) {
            return "redirect:/home";
        }
        if (page == null) {
            page = 1;
        }
        if ("remove".equals(action)) {
            Map<String, Object> map = searchLogic.searchBySellerId(sessionUser.getId(), false, page);
            model.putAll(map);
        } else if ("restore".equals(action)) {
            Map<String, Object> map = searchLogic.searchBySellerId(sessionUser.getId(), true, page);
            model.putAll(map);
        }
        return "seller.jsp";
    }

    @RequestMapping(value = "/seller/add", method = RequestMethod.POST)
    public ResponseEntity<String> sellerPost(int typeId,
                                             String title,
                                             String[] authors,
                                             String venue,
                                             int year,
                                             String description,
                                             Double price,
                                             MultipartFile image,
                                             HttpSession session) {
        User sessionUser = (User) session.getAttribute(ATTR_USER);
        if (sessionUser == null) {
            return ResponseEntity.badRequest().body("not login");
        }
        if (sessionUser.getType() != 1) {
            return ResponseEntity.badRequest().body("not a bookseller");
        }
        try {
            Publication publication = new Publication();
            publication.setRemoved(false);
            publication.setSellerId(sessionUser.getId());
            publication.setTypeId(typeId);
            publication.setTitle(title);
            publication.setVenue(venue);
            publication.setYear(year);
            if (description != null && !description.isEmpty()) {
                publication.setDescription(description);
            }
            publication.setPrice(BigDecimal.valueOf(price));
            if (image != null) {
                String staticDir = session.getServletContext().getRealPath("/static");
                FileCopyUtils.copy(image.getBytes(), new File(staticDir + "/" + image.getOriginalFilename()));
                publication.setImagePath("/static/" + image.getOriginalFilename());
            }
            userLogic.publicationAdd(publication, authors);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/seller/toggleremoved", method = RequestMethod.POST)
    public ResponseEntity<String> sellerPost(int id, HttpSession session) {
        User sessionUser = (User) session.getAttribute(ATTR_USER);
        if (sessionUser == null) {
            return ResponseEntity.badRequest().body("not login");
        }
        if (sessionUser.getType() != 1) {
            return ResponseEntity.badRequest().body("not a bookseller");
        }
        Publication publication = publicationMapper.selectByPrimaryKey(id);
        if (!publication.getSellerId().equals(sessionUser.getId())) {
            return ResponseEntity.badRequest().body("wrong seller_id");
        }
        publicationMapper.updateRemovedById(id, !publication.getRemoved());
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<String> upload(MultipartFile file, HttpSession session) throws IOException {
        String staticDir = session.getServletContext().getRealPath("/static");
        FileCopyUtils.copy(file.getBytes(), new File(staticDir + "/" + file.getOriginalFilename()));
        return ResponseEntity.ok("upload finished");
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(@RequestParam int id, Map<String, Object> model) {
        //id = Math.max(id, 0);
        Publication publication = searchLogic.searchById(id);
        model.put("publ", publication);
        return "info.jsp";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam MultiValueMap<String, String> params, Map<String, Object> model) {
        int page = 1;
        if (params.containsKey("page")) {
            String pageString = params.getFirst("page");
            try {
                page = Integer.parseInt(pageString);
            } catch (NumberFormatException e) {
                logger.warn(e.getMessage());
            }
        }

        if (params.containsKey("keyword")) {
            String keyword = params.getFirst("keyword");
            model.putAll(searchLogic.searchByKeyword(keyword, page, false));

        } else {
            Map<String, Object> condition = new HashMap<>();
            String[] paramNames = {"title", "author", "venue"};
            for (String paramName : paramNames) {
                if (params.containsKey(paramName) && !params.getFirst(paramName).isEmpty()) {
                    condition.put(paramName, params.getFirst(paramName));
                }
            }
            if (params.containsKey("yearfrom") && !params.getFirst("yearfrom").isEmpty()) {
                try {
                    Integer yearFrom = Integer.parseInt(params.getFirst("yearfrom"));
                    condition.put("yearfrom", yearFrom);
                    Integer yearTo = yearFrom;
                    if (params.containsKey("yearto") && !params.getFirst("yearto").isEmpty()) {
                        try {
                            yearTo = Integer.parseInt(params.getFirst("yearto"));
                        } catch (NumberFormatException e) {
                            logger.warn(e.getMessage());
                        }
                    }
                    condition.put("yearto", yearTo);
                } catch (NumberFormatException e) {
                    logger.warn(e.getMessage());
                }
            }
            if (params.containsKey("type") && params.get("type").size() > 0) {
                condition.put("type", params.get("type"));
            }
            model.putAll(searchLogic.searchByCondition(condition, page));
        }
        return "search.jsp";
    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String adminLogin() {
        return "adminlogin.jsp";
    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public String adminLoginPost(String username, String password, Map<String, Object> map, HttpSession session) {
        User admin = userMapper.selectByUsername(username);
        String message = null;
        boolean invalid = false;
        if (admin == null)  {
            message = "username does not exist";
            invalid = true;
        } else if (!admin.getPassword().equals(password)) {
            message = "wrong password";
            invalid = true;
        } else if (admin.getType() != 2) {
            message = "not an admin";
            invalid = true;
        }
        if (invalid) {
            map.put("message", message);
            return "adminlogin.jsp";
        }
        session.setAttribute(ATTR_ADMIN, admin);
        return "redirect:/admin?action=remove";
    }

    @RequestMapping(value = "/admin/logout")
    public String adminLogout(HttpSession session) {
        session.removeAttribute(ATTR_ADMIN);
        return "redirect:/admin/login";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(String action,
                        String keyword,
                        Integer page,
                        Map<String, Object> model,
                        HttpSession session) {
        User admin = (User) session.getAttribute(ATTR_ADMIN);
        if (admin == null || admin.getType() != 2) {
            return "redirect:/admin/login";
        }
        if ("remove".equals(action)) {
            if (keyword == null) {
                keyword = "";
            }
            if (page == null) {
                page = 1;
            }
            model.putAll(searchLogic.searchByKeyword(keyword, page, null));
        } else if ("user".equals(action)) {
            List<User> customers = userMapper.selectByType(0);
            model.put("customers", customers);
        }
        return "admin.jsp";
    }

    @RequestMapping(value = "/admin/togglebanned", method = RequestMethod.POST)
    public ResponseEntity<String> toggleBanned(int id, HttpSession session) {
        User admin = (User) session.getAttribute(ATTR_ADMIN);
        if (admin == null) {
            return ResponseEntity.badRequest().body("not login");
        } else if (admin.getType() != 2) {
            return ResponseEntity.badRequest().body("not an admin");
        }
        User customer = userMapper.selectByPrimaryKey(id);
        if (customer == null) {
            return ResponseEntity.badRequest().body("wrong user id");
        }
        userMapper.updateBannedById(customer.getId(), !customer.getBanned());
        return ResponseEntity.ok("update succeeded");
    }

    @RequestMapping(value = "/admin/remove", method = RequestMethod.POST)
    public ResponseEntity<String> adminRemove(int id, HttpSession session) {
        User admin = (User) session.getAttribute(ATTR_ADMIN);
        if (admin == null) {
            return ResponseEntity.badRequest().body("not login");
        } else if (admin.getType() != 2) {
            return ResponseEntity.badRequest().body("not an admin");
        }
        Publication publication = publicationMapper.selectByPrimaryKey(id);
        if (publication == null) {
            return ResponseEntity.badRequest().body("publication doesn't exist");
        }
        publicationMapper.deleteByPrimaryKey(publication.getId());
        return ResponseEntity.ok("remove succeeded");
    }

    @RequestMapping(value = "/admin/userstat", method = RequestMethod.GET)
    public String adminUserStat(int id, Map<String, Object> model, HttpSession session) {
        User admin = (User) session.getAttribute(ATTR_ADMIN);
        if (admin == null || admin.getType() != 2) {
            return "redirect:/admin/login";
        }
        User user = userMapper.selectByPrimaryKey(id);
        List<Order> orders = orderMapper.selectByUserId(id, true);
        model.put("user", user);
        model.put("orders", orders);
        return "userstat.jsp";
    }

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    @ResponseBody
    public String mail(String to, String title, String content) {
        Utils.sendEmail(to, title, content);
        return "ok";
    }
}

/*List<Publication> publications = entityMapper.selectedAttributes();
for(Publication pub : publications) 
{  	
	Entity entry1 = new Entity();	
	entry1.setEntityId(pub.getId());  			
	entry1.setEntityAttribute("Type");
	entry1.setAttributeValue(pub.getType());
	entityMapper.insertPub(entry1);
	
	Entity entry2 = new Entity();	
	entry2.setEntityId(pub.getId());  	
	entry2.setEntityAttribute("Class");
	entry2.setAttributeValue("entityNode");  		
	entityMapper.insertPub(entry2);
	
	Entity entry3 = new Entity();	
	entry3.setEntityId(pub.getId());  	
	entry3.setEntityAttribute("Title");
	entry3.setAttributeValue(pub.getTitle());  		
	entityMapper.insertPub(entry3);	
}

List<Person> persons = entityMapper.selectAllPersons();   
for(Person person : persons)
{
	Entity entry1 = new Entity();	
	entry1.setPersonEntityId(person.getId());  			
	entry1.setEntityAttribute("Type");
	entry1.setPersonAttrValueType("Venue");
	entityMapper.insertPerson(entry1);
	
	Entity entry2 = new Entity();	
	entry2.setPersonEntityId(person.getId());  	
	entry2.setEntityAttribute("Class");
	entry2.setPersonAttrValueType("entityNode");  		
	entityMapper.insertPerson(entry2);
	
	Entity entry3 = new Entity();	
	entry3.setPersonEntityId(person.getId());  	
	entry3.setEntityAttribute("Name");
	entry3.setPersonAttrValueType(person.getName());  		
	entityMapper.insertPerson(entry3);
}

List<Venue> venue = entityMapper.selectVenues();
for(Venue v : venue)
{
	Entity entry1 = new Entity();	
	entry1.setVenueEntityId(v.getId());  			
	entry1.setEntityAttribute("Type");
	entry1.setAttributeValue("Venue");
	entityMapper.insertVenue(entry1);
	
	Entity entry2 = new Entity();	
	entry2.setVenueEntityId(v.getId());  	
	entry2.setEntityAttribute("Class");
	entry2.setAttributeValue("entityNode");  		
	entityMapper.insertVenue(entry2);
	
	Entity entry3 = new Entity();	
	entry3.setVenueEntityId(v.getId());  	
	entry3.setEntityAttribute("Name");
	entry3.setAttributeValue(v.getName());  		
	entityMapper.insertVenue(entry3);
}

List<PublPerson> edges = entityMapper.selectLink();

List<Entity> tmpList = new LinkedList<>();
List<Graph> tmpGraph = new LinkedList<>();
List<Publication> pubList = new LinkedList<>();    		
int setEdgeId = 1;
int counter = 0;

for(PublPerson pp : edges)
{	  				 		
	Graph g = new Graph();
	
	Entity entryS = new Entity();
	entryS.setEdgeEntityId(setEdgeId);
	entryS.setEntityAttribute("Type");
	entryS.setAttributeValue("directedLink");
	tmpList.add(entryS);    
		
	Entity entryE = new Entity();
	entryE.setEdgeEntityId(setEdgeId);
	entryE.setEntityAttribute("Class");
	entryE.setAttributeValue("Edge");
	tmpList.add(entryE);
		
	Entity entryO = new Entity();
    entryO.setEdgeEntityId(setEdgeId);
    entryO.setEntityAttribute("Label");
    entryO.setAttributeValue("authoredBy");
    tmpList.add(entryO);

	g.setNodeFrom("P"+pp.getPublId());	
    g.setNodeEdge("E"+setEdgeId);
	g.setNodeTo("A"+pp.getAuthor());
	      	
	Publication pubRecord = entityMapper.selectByPubId(pp.getPublId());
	if(pubRecord.getVenueId() != null)
	{    
		pubList.add(pubRecord);	
	}
	tmpGraph.add(g);
	setEdgeId++;
}
for(Publication p : pubList)
{  			
	Graph g2 = new Graph();
    if(p.getId() != counter)
    {     
    	counter = p.getId();
    	
        Entity v1 = new Entity();
    	v1.setEdgeEntityId(setEdgeId);		
    	v1.setEntityAttribute("Type");
    	v1.setAttributeValue("directedLink");
    	tmpList.add(v1);
    	    	        		
    	Entity v2 = new Entity();
    	v2.setEdgeEntityId(setEdgeId);
    	v2.setEntityAttribute("Class");
    	v2.setAttributeValue("Edge");
    	tmpList.add(v2);
    	    	    			
    	Entity v3 = new Entity();
    	v3.setEdgeEntityId(setEdgeId);
    	v3.setEntityAttribute("Label");
    	v3.setAttributeValue("publishedIn");
    	tmpList.add(v3);
    	    	   
    	g2.setNodeFrom("P"+p.getId());
    	g2.setNodeEdge("E"+setEdgeId);
    	g2.setNodeTo("V"+p.getVenueId());  	
           
	    tmpGraph.add(g2);
	    setEdgeId++;
    }
} 	
for(Entity e : tmpList){
	entityMapper.insertEdge(e);  
}
for(Graph gList : tmpGraph){
	entityMapper.insertGraph(gList);  
}

List<Graph> graphStore = entityMapper.selectAll();
entityMapper.deleteAll();
for(Graph g : graphStore)
{ 		
	entityMapper.insertGraph(g);
}*/