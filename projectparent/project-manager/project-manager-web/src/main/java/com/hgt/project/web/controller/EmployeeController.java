package com.hgt.project.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgt.project.common.util.Response;
import com.hgt.project.dao.entity.Employee;
import com.hgt.project.service.IEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xavier
 * @since 2019-12-17
 */
@RestController
@RequestMapping("/project/employee")
@Slf4j
@Api(tags = "员工信息")
public class EmployeeController {

    private List<OrderItem> orderItems = new ArrayList<>();

    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiOperation(value = "员工列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", dataType = "int", value = "当前页"),
            @ApiImplicitParam(name = "size", dataType = "int", value = "页大小"),
            @ApiImplicitParam(name = "name", dataType = "String", value = "服务名称")
    })
    @GetMapping(path = "/page")
    public ResponseEntity<Response> list(@RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                                         @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                         @RequestParam(value = "username", required = false, defaultValue = "") String username
    ) {
        log.info("分页查询服务内容信息");
        Page<Employee> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        orderItems.clear();
        // 这里排序使用的字段名称对应数据库的字段名称
        orderItems.add(new OrderItem().setColumn("update_time").setAsc(false));
        page.setOrders(orderItems);
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNoneBlank(username),"name", username);
        IPage<Employee> mapIPage;
        try {
            mapIPage = employeeService.page(page, wrapper);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "分页服务记录", mapIPage));
    }

    @ApiOperation(value = "添加员工", httpMethod = "POST")
    @PostMapping("/save")
    public ResponseEntity<Response> save(Employee employee) {
        log.info("添加员工信息");
        try {
            employeeService.save(employee);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "保存员工信息成功！", employee));
    }

    @ApiOperation(value = "更新员工", httpMethod = "POST")
    @PostMapping(value = "/update")
    public ResponseEntity<Response> update(Employee employee) {
        log.info("更新员工信息");
        try {
            employeeService.updateById(employee);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "更新员工信息成功！", employee));
    }

    @ApiOperation(value = "删除员工", httpMethod = "DELETE")
    @DeleteMapping("/delete")
    public ResponseEntity<Response> delete(@RequestParam("ids") Set<Integer> ids) {
        log.info("删除员工信息");
        try {
            employeeService.removeByIds(ids);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "删除员工信息成功！", ids));
    }

}
