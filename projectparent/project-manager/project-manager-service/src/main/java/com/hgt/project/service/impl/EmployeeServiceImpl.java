package com.hgt.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hgt.project.dao.entity.Employee;
import com.hgt.project.dao.mapper.EmployeeMapper;
import com.hgt.project.service.IEmployeeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xavier
 * @since 2019-12-17
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
