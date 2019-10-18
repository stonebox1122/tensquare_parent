package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.List;

@Service
@Transactional
public class SpitService {
    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    public void save(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spitDao.save(spit);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentid(String parentid, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid, pageable);
    }

    public void thumbup(String spitId) {
        //方式1效率有问题
        //Spit spit = spitDao.findById(spitId).get();
        //spit.setThumbup((spit.getThumbup()==null ? 0 : spit.getThumbup()) + 1);
        //spitDao.save(spit);

        //方式2使用原生mongo命令实现自增db.spit.update({"_id":"1"},{$inc:{thumbup:NumberInt(1)}})
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
