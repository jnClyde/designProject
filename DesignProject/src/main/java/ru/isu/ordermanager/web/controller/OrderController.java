package ru.isu.ordermanager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.isu.ordermanager.domain.model.Order;
import ru.isu.ordermanager.domain.repository.ClientRepository;
import ru.isu.ordermanager.domain.repository.OrderRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/orders")
public class OrderController {
//форма для добавления заказа теперь работает :)
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/findall")
    public String findAll(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "maininfo/orders";
    }

    @RequestMapping(value="/findallpages")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showPageTable(Pageable page, Model model, Sort sort)  {
        Sort.Order o = null;
        if (sort != null)
            o = sort.iterator().next();
        model.addAttribute("sort", (sort != null?o.getProperty():""));
        model.addAttribute("dir", (sort != null?o.getDirection():""));
        model.addAttribute("page", orderRepository.findAll(page));
        return "maininfo/allOrders";
    }

    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String addBus(Model model){
        model.addAttribute("order", new Order());
        return "addOrder";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveBus(
            @Valid @ModelAttribute("order") Order order,
            BindingResult errors,
            Model model){
        //System.out.println(order);
        if(errors.hasErrors()){
            return "addOrder";
        }
        orderRepository.save(order);
        return "redirect:/orders/findorder";
    }

    @RequestMapping(value = "/order")
    public String findOne(@RequestParam("orderId") Integer orderId, Model model) {
        Order order = orderRepository.findOne(orderId);
        model.addAttribute("order", order);
        return "maininfo/order";
    }

    //JSON
    @RequestMapping("/loadall")
    public String loadAll(){
        return "loadOrders";
    }

    @RequestMapping("/getall")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody List<Order> getAll(){
        return orderRepository.findAll();
    }

    @RequestMapping("/all")
    public List<Order> getAllOrders() {
        System.out.println(orderRepository.findAll());
        return this.orderRepository.findAll();
    }

    @RequestMapping(value = "/selectorders")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String selectForm(Model model) {
        return "select/selectOrder";
    }


    //select by rank and sort
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String showPageTable(@RequestParam("type-sort") String sort,
                                @RequestParam("order-rank") String rank,
                                @RequestParam("order-rank1") String rank1,
                                @RequestParam("type-select") String select){
        Model model;
        Sort sorts = new Sort(sort);
        if (rank.length() == 0) return "select/selectOrder";
        Double r = Double.parseDouble(rank);
        Double r1 = 0.0;
        if (rank1.length() != 0) r1 = Double.parseDouble(rank1);
        Sort.Order o = null;
        if (sorts != null) o = sorts.iterator().next();
        System.out.println(sort + " " + r + " " + r1 + " " + select);
        return "redirect:/orders/selected?page=0&sort="+o.getProperty()+","+o.getDirection()+
                "&rank="+r+"&rank1="+r1+"&select="+select;
    }

    @RequestMapping(value = "/selected", method = RequestMethod.GET)
    public String showPageTable(Pageable page, Model model,
                                Sort sort,
                                Double rank,
                                Double rank1,
                                String select) {
        Pageable pageNew = new PageRequest(page.getPageNumber(),5, sort);
        System.out.println(sort + " " + rank + " " + rank1 + " " + select);
        Sort.Order o = null;
        if (sort != null) o = sort.iterator().next();
        model.addAttribute("sort", sort != null ? o.getProperty() : "");
        model.addAttribute("dir", (sort!=null)?o.getDirection() : "");
        model.addAttribute("rank", rank);
        model.addAttribute("rank1", rank1);
        model.addAttribute("select", select);
        if (select.equals("Equals")) model.addAttribute("page", orderRepository.findAllByRank(rank , pageNew));
        else if (select.equals("Less_than"))  model.addAttribute("page", orderRepository.findAllByRankBefore(rank , pageNew));
        else if (select.equals("More_than"))  model.addAttribute("page", orderRepository.findAllByRankAfter(rank , pageNew));
        else if (select.equals("Between")) {
            if (rank1 == 0.0) return "select/selectOrder";
            model.addAttribute("page", orderRepository.findAllByRankBetween(rank, rank1, pageNew));
        }
        return "select/ordersPage";
    }

    //find by order title
    @RequestMapping(value = "/findorder")
    public String findForm(Model model) {
        return "find/findOrder";
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String showPageOrder(@RequestParam("order-title") String title){
        if (title.length() == 0) return "find/findOrder";
        return "redirect:/orders/found?page=0&title="+title;
    }

    @RequestMapping(value = "/found", method = RequestMethod.GET)
    public String showPageOrder(Pageable page, Model model,
                                String title) {
        Pageable pageNew = new PageRequest(page.getPageNumber(),1);
        model.addAttribute("title", title);
        model.addAttribute("page", orderRepository.findAllByTitleIsContaining(title, page));
        return "find/oneOrder";
    }


    //find by client name/surname/username
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/findclient")
    public String findClient(Model model) {
        return "find/clientForm";
    }

    @RequestMapping(value = "/findbyclient", method = RequestMethod.POST)
    public String showPageClient(@RequestParam("order-client") String client,
                                 @RequestParam("type-select") String select){
        if (client.length() == 0) return "find/clientForm";
        return "redirect:/orders/foundclient?page=0&client="+ client+"&select="+select;
    }

    @RequestMapping(value = "/foundclient", method = RequestMethod.GET)
    public String showPageClient(Pageable page, Model model,
                                String client, String select) {
        Pageable pageNew = new PageRequest(page.getPageNumber(),1);
        model.addAttribute("client", client);
        if (select.equals("Name"))
            model.addAttribute("page", orderRepository.findAllByClientsFirstName(client, page));
        else if (select.equals("Surname"))
            model.addAttribute("page", orderRepository.findAllByClientsLastName(client, page));
        else model.addAttribute("page", orderRepository.findAllByClientsUsername(client, page));
        return "find/clients";
    }

    //send mail
    @RequestMapping(value = "/sendForm")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String sendMail(Model model){
        return "sendm/sendForm";
    }

    @RequestMapping(value = "/sendmail")
    public String sendMessage(
                       @RequestParam("mail-client") String client,
                       @RequestParam("mail-txt") String txt,
                       @RequestParam("mail-sub") String sub,
                       @RequestParam("mail-path") String path)
            throws UnsupportedEncodingException, MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.yandex.ru");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("t4dmindesign@yandex.ru", "t4dmindesign1");
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("t4dmindesign@yandex.ru"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(clientRepository.findByUsername(client).getEmail()));
        message.setSubject(sub);
        if (path.length() == 0) message.setText(txt);
        else message.setText(txt + "; link: " + path);
        Transport.send(message);
        return "sendm/message";
    }


    @ModelAttribute("typeofselect")
    public List<String> getTypeofSort(){
        return Arrays.asList("Equals", "More_than", "Less_than", "Between") ;
    }
    @ModelAttribute("typeoffind")
    public List<String> getTypeofFind(){
        return Arrays.asList("Name", "Surname", "Username") ;
    }

    @ModelAttribute("typeofsort")
    public List<String> getSorts() {
        return new LinkedList<>(Arrays.asList("status", "rank", "deadline"));
    }

    @RequestMapping(value="/errors", method = RequestMethod.GET)
    public ModelAndView errorPage(HttpServletRequest httpRequest) {
        ModelAndView errorPage = new ModelAndView("pages_error");
        int errorCode = (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
        String errorMsg = "Error #"+ errorCode;
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }
}
