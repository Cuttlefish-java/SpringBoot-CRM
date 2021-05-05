package com.zx.crm.workbench.controller;

import com.zx.crm.settings.model.User;
import com.zx.crm.settings.service.UserService;
import com.zx.crm.util.DateTimeUtil;
import com.zx.crm.util.UUIDUtil;
import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.model.Customer;
import com.zx.crm.workbench.model.CustomerRemark;
import com.zx.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @RequestMapping("/index")
    public String workbenchCustomerIndex(){

        return "workbench/customer/index";
    }
    @RequestMapping("/detail")
    public String workbenchCustomerDetail(String customerId, Model model){
        System.out.println(customerId);
        Customer customer = customerService.getCustomerById1(customerId);
        System.out.println(customer);
        List<CustomerRemark> customerRemarks = customerService.getRemarks(customerId);
        model.addAttribute("customer",customer);
        model.addAttribute("customerRemarks",customerRemarks);
        return "workbench/customer/detail";
    }
    @RequestMapping("saveCustomer")
    @ResponseBody
    public boolean saveCustomer(HttpServletRequest request,Customer customer){
        customer.setId(UUIDUtil.getUUID());
        User user = (User) request.getSession().getAttribute("user");
        customer.setCreateBy(user.getName());
        customer.setCreateTime(DateTimeUtil.getSysTime());
        boolean flag = customerService.saveCustomer(customer);
        return flag;
    }
    @RequestMapping("/pageList")
    @ResponseBody
    public PaginationVO<Customer> pageList(Integer pageNo,Integer pageSize,Customer customer){
        System.out.println(pageNo);
        System.out.println(pageSize);
        System.out.println(customer);
        PaginationVO<Customer> paginationVO = customerService.pageList(pageNo,pageSize,customer);
        return paginationVO;
    }
    @RequestMapping("/getCustomerByIdAndUserList")
    @ResponseBody
    public Map<String,Object> getCustomerByIdAndUserList( String id){
        List<User> users = userService.getUserList();
        Customer customer = customerService.getCustomerById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("userList",users);
        map.put("customer",customer);
        return map;
    }
    @RequestMapping("/updateCustomer")
    @ResponseBody
    public boolean updateCustomer( Customer customer){
        boolean flag = customerService.updateCustomer(customer);
        return flag;
    }

    @RequestMapping("/deleteCustomer")
    @ResponseBody
    public boolean deleteCustomer( String[] id){
        boolean flag = customerService.deleteCustomer(id);
        System.out.println(flag);
        return flag;
    }
    @RequestMapping("/getCustomerRemarkByCustomerId")
    @ResponseBody
    public Map<String,Object> getCustomerRemarkByCustomerId(String customerId){
        List<CustomerRemark> customerRemarks = customerService.getRemarks(customerId);
        Map<String,Object> map = new HashMap<>();
        map.put("customerRemarks",customerRemarks);
        return map;
    }

    @RequestMapping("/saveRemark")
    @ResponseBody
    public boolean saveRemark(CustomerRemark customerRemark,HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        customerRemark.setCreateTime(DateTimeUtil.getSysTime());
        customerRemark.setCreateBy(user.getName());
        customerRemark.setId(UUIDUtil.getUUID());
        customerRemark.setEditFlag("0");
        System.out.println(customerRemark);
        boolean flag = customerService.saveRemark(customerRemark);
        return flag;
    }
    @RequestMapping("/editCustomerRemark")
    @ResponseBody
    public CustomerRemark saveRemark(String id){
        CustomerRemark customerRemark = customerService.getRemark(id);
        return customerRemark;
    }


}
