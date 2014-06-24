package com.wansan.template.service;

import com.wansan.template.model.Person;
import org.junit.Test;

/**
 * Created by Administrator on 14-4-15.
 */
public class PersonServiceTest {
    @Test
    public void testTxSave() throws Exception {
        Person person = new Person();
        person.setName("Huangwenhai");
        person.setPassword("config123456");
        PersonService personService = new PersonService();
        personService.txSave(person,null);
    }
}
