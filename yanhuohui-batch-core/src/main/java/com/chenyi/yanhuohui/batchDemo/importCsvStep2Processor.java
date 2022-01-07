package com.chenyi.yanhuohui.batchDemo;

import com.chenyi.yanhuohui.client.Client;
import com.chenyi.yanhuohui.client.ClientRepository;
import com.chenyi.yanhuohui.manager.Manager;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class importCsvStep2Processor implements ItemProcessor<Manager,Client> {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client process(Manager item) throws Exception {
        Optional<Client> client = clientRepository.findById(item.getId());
        if(client.isPresent()){
            client.get().setRole(item.getRole());
            //clientRepository.save(client.get());
            return client.get();
        }else {
            return null;
        }
//        if (item.getRole().equals("A")) {
//            item.setRole("男");
//        } else if(item.getRole().equals("B")){
//            item.setRole("女");
//        } else {
//            item.setRole("未知");
//        }
    }
}
