package com.chenyi.yanhuohui.abstractpageread;

import com.chenyi.yanhuohui.primary.manager.Manager;
import com.chenyi.yanhuohui.primary.manager.ManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Classname DemoPagingItemReader
 * @Description TODO
 * @Date 2025/1/9 20:01
 * @Created by 陈义
 */
@Slf4j
public class DemoPagingItemReader extends AbstractPagingItemReader<Manager> {

    @Autowired
    private ManagerRepository managerRepository;

    public DemoPagingItemReader(int pageSize) {
        this.setPageSize(pageSize);
    }

    @Override
    protected void doReadPage() {
        if (this.results == null) {
            this.results = new CopyOnWriteArrayList<>();
        } else {
            this.results.clear();
        }

        // 分页查询
        Page<Manager> managerPage = managerRepository.findAll(PageRequest.of(getPage(),getPageSize()));

        this.results.addAll(managerPage.getContent());
        log.info("======================第{}页=====================",getPage());
    }

    @Override
    protected void doJumpToPage(int i) {
        // 这里不涉及到跳转到指定页面，所以这里为空实现
    }
}
