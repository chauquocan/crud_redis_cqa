package com.example.crud_new.repository;

import com.example.crud_new.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private HashOperations hashOperations;//crud hash

    private ListOperations listOperations;//curd list

    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {
//        this.hashOperations = redisTemplate.opsForHash();//hash
        this.listOperations = redisTemplate.opsForList();//list
        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee){
//        hashOperations.put("EMPLOYEE", employee.getId(), employee);//hash
        listOperations.leftPush("EMPLOYEE",employee);//list

    }
    public List<Employee> findAll(){
//        return hashOperations.values("EMPLOYEE");
        return listOperations.range("EMPLOYEE",0,listOperations.size("EMPLOYEE"));
    }
    public Employee findById(Integer id){
//        return (Employee) hashOperations.get("EMPLOYEE", id);//hash
        //list
        List<Employee> list = listOperations.range("EMPLOYEE",0,listOperations.size("EMPLOYEE"));
        for (Employee employee : list){
            if(employee.getId()==id){
                return employee;
            }
        }
        return null;
        //
    }

    public void update(Employee employee){
        saveEmployee(employee);
    }

    public void delete(Integer id){
//        hashOperations.delete("EMPLOYEE", id);//hash
        listOperations.rightPopAndLeftPush("EMPLOYEE",id);//list
    }
}
