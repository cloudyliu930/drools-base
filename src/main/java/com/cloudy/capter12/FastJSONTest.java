package com.cloudy.capter12;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.serializer.BeforeFilter;
import org.drools.workbench.models.guided.template.shared.TemplateModel;
import org.junit.Test;

/**
 * @author liuchuangui
 * @createTime 2018/12/12
 * @description
 */
public class FastJSONTest {

    @Test
    public void testToJson() {
        Address address = new Address();
        address.setName("上海市");
        address.setDetail("浦东新区金口路");

        Person person = new Person();
        person.setAddress(address);
        person.setName("张三");
        person.setAge(100);

        String json = JSONObject.toJSONString(person, new BeforeFilter() {
            @Override
            public void writeBefore(Object object) {
                System.out.println(object);
                super.writeKeyValue("xtype", object.getClass().getName());
            }
        });

        System.out.println(json);
    }

    @Test
    public void testToObject() {
        Person person = JSON.parseObject("{\"xtype\":\"com.cloudy.capter12.Person\",\"address\":{\"xtype\":\"com.cloudy.capter12.Address\",\"detail\":\"浦东新区金口路\",\"name\":\"上海市\"},\"age\":100,\"name\":\"张三\"}",
                Person.class);
        System.out.println(person);
    }

    @Test
    public void testToTemplateRule() {
        TemplateModel templateModel = JSON.parseObject("{\"status\":0,\"packageId\":\"20\",\"ruleType\":\"DEFAULT\",\"gatherId\":12,\"imports\":[],\"lhs\":[ {\"constraintList\":{\"xtype\":\"org.drools.workbench.models.datamodel.rule.CompositeFieldConstraint\",\"factType\":\"RunContext\",\"others\":{\"closed\":false},\"compositeJunctionType\":\",\",\"constraints\":[{\"xtype\":\"org.drools.workbench.models.datamodel.rule.CompositeFieldConstraint\",\"compositeJunctionType\":\"&&\",\"constraints\":[{\"xtype\":\"org.drools.workbench.models.datamodel.rule.SingleFieldConstraint\",\"factType\":\"Variables\",\"leftField\":{\"fieldType\":\"VariableField\",\"showName\":\"username\",\"valueType\":\"String\",\"fieldName\":\"vs.get(\\\"username\\\", \\\"String\\\", \\\"用户名\\\")\",\"name\":\"username\",\"displayName\":\"用户名\",\"variableType\":\"VariablePool\"},\"rightField\":{\"fieldType\":\"StringField\",\"value\":\"2\"},\"others\":{},\"operator\":\"==\"},{\"xtype\":\"org.drools.workbench.models.datamodel.rule.SingleFieldConstraint\",\"factType\":\"Variables\",\"leftField\":{\"fieldType\":\"VariableField\",\"showName\":\"age\",\"valueType\":\"Integer\",\"fieldName\":\"vs.get(\\\"age\\\", \\\"Integer\\\", \\\"年龄\\\")\",\"name\":\"age\",\"displayName\":\"年龄\",\"variableType\":\"VariablePool\"},\"rightField\":{\"fieldType\":\"StringField\",\"value\":\"30\"},\"others\":{},\"operator\":\">\"}]}]},\"factType\":\"Variables\",\"boundName\":\"vs\"}],\"attributes\":[{\"attributeName\":\"dialect\",\"value\":\"mvel\"},{\"attributeName\":\"ruleflow-group\",\"value\":\"blackrule\"}],\"ruleName\":\"222\",\"salience\":100,\"rhs\":[{\"xtype\":\"org.drools.workbench.models.datamodel.rule.ActionCallMethod\",\"methodName\":\"append\",\"variable\":\"vs\",\"left\":{\"displayName\":\"reasonCode\",\"key\":\"reasonCode\",\"name\":\"原因编码码\",\"type\":\"String\"},\"right\":{\"fieldType\":\"StringField\",\"value\":\"222\",\"result\":\"true\"}},{\"xtype\":\"org.drools.workbench.models.datamodel.rule.ActionCallMethod\",\"methodName\":\"plus\",\"variable\":\"vs\",\"left\":{\"key\":\"finalScore\",\"name\":\"人员评分\",\"type\":\"Integer\",\"displayName\":\"finalScore\"},\"right\":{\"fieldType\":\"StringField\",\"result\":\"true\",\"value\":\"10\"}}],\"name\":\"22\",\"activationGroup\":\"2\",\"displayStyle\":false}", TemplateModel.class, new ExtraProcessor() {
            @Override
            public void processExtra(Object object, String key, Object value) {
                if (key == "xtype") {
                    try {
                        object = Class.forName(value.toString());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(key);
                System.out.println(value);
            }
        });
        System.out.println(templateModel);

        JSONObject jsonObject = JSON.parseObject("{\"status\":0,\"packageId\":\"20\",\"ruleType\":\"DEFAULT\",\"gatherId\":12,\"imports\":[],\"lhs\":[ {\"constraintList\":{\"xtype\":\"org.drools.workbench.models.datamodel.rule.CompositeFieldConstraint\",\"factType\":\"RunContext\",\"others\":{\"closed\":false},\"compositeJunctionType\":\",\",\"constraints\":[{\"xtype\":\"org.drools.workbench.models.datamodel.rule.CompositeFieldConstraint\",\"compositeJunctionType\":\"&&\",\"constraints\":[{\"xtype\":\"org.drools.workbench.models.datamodel.rule.SingleFieldConstraint\",\"factType\":\"Variables\",\"leftField\":{\"fieldType\":\"VariableField\",\"showName\":\"username\",\"valueType\":\"String\",\"fieldName\":\"vs.get(\\\"username\\\", \\\"String\\\", \\\"用户名\\\")\",\"name\":\"username\",\"displayName\":\"用户名\",\"variableType\":\"VariablePool\"},\"rightField\":{\"fieldType\":\"StringField\",\"value\":\"2\"},\"others\":{},\"operator\":\"==\"},{\"xtype\":\"org.drools.workbench.models.datamodel.rule.SingleFieldConstraint\",\"factType\":\"Variables\",\"leftField\":{\"fieldType\":\"VariableField\",\"showName\":\"age\",\"valueType\":\"Integer\",\"fieldName\":\"vs.get(\\\"age\\\", \\\"Integer\\\", \\\"年龄\\\")\",\"name\":\"age\",\"displayName\":\"年龄\",\"variableType\":\"VariablePool\"},\"rightField\":{\"fieldType\":\"StringField\",\"value\":\"30\"},\"others\":{},\"operator\":\">\"}]}]},\"factType\":\"Variables\",\"boundName\":\"vs\"}],\"attributes\":[{\"attributeName\":\"dialect\",\"value\":\"mvel\"},{\"attributeName\":\"ruleflow-group\",\"value\":\"blackrule\"}],\"ruleName\":\"222\",\"salience\":100,\"rhs\":[{\"xtype\":\"org.drools.workbench.models.datamodel.rule.ActionCallMethod\",\"methodName\":\"append\",\"variable\":\"vs\",\"left\":{\"displayName\":\"reasonCode\",\"key\":\"reasonCode\",\"name\":\"原因编码码\",\"type\":\"String\"},\"right\":{\"fieldType\":\"StringField\",\"value\":\"222\",\"result\":\"true\"}},{\"xtype\":\"org.drools.workbench.models.datamodel.rule.ActionCallMethod\",\"methodName\":\"plus\",\"variable\":\"vs\",\"left\":{\"key\":\"finalScore\",\"name\":\"人员评分\",\"type\":\"Integer\",\"displayName\":\"finalScore\"},\"right\":{\"fieldType\":\"StringField\",\"result\":\"true\",\"value\":\"10\"}}],\"name\":\"22\",\"activationGroup\":\"2\",\"displayStyle\":false}");


        System.out.println(jsonObject);
    }
}


class Person {
    private String name;
    private Integer age;
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                '}';
    }
}

class Address {
    private String name;
    private String detail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}