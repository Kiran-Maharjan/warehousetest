package com.example.warehousetest.dao;
import com.example.warehousetest.model.AccumulatedDeal;
import com.example.warehousetest.model.DealModel;
import com.example.warehousetest.model.InValidDeal;
import com.example.warehousetest.model.ValidDeal;
import com.example.warehousetest.service.impl.FileService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Transactional
@Repository
public class FileUploadDao implements IFileUploadDAO {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${hibernate.jdbc.batch_size}")
	private int batchSize;

	@Override
	public boolean fileExists(String fileName) {
		Session session = sessionFactory.openSession();
		return ((Long) session.createQuery("select count(*) from ValidDeal where fileName=:fileName").setParameter("fileName", fileName).uniqueResult()).intValue() > 0;
	}

	// invalid deal saving
	@Override
	public <T extends InValidDeal> Collection<T> bulkInvalidSave(Collection<T> entities) {
		logger.info( "Invalid deal saving");
		return saveEntities(entities);
	}
	//valid deal saving
	@Override
	public <T extends ValidDeal> Collection<T> bulkValidSave(Collection<T> entities, List<AccumulatedDeal> accumulatedDeals) {
		logger.info( "Valid deal saving");
		saveAccumulativeDeals(accumulatedDeals);
		return saveEntities(entities);
	}


	private <T extends DealModel>Collection<T> saveEntities(Collection<T> entities) {
		final List<T> savedEntities = new ArrayList<T>(entities.size());

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		int i = 0;
		for (T t : entities) {
			session.save(t);
			i++;
			if (i % batchSize == 0) {
				session.flush();
				session.clear();
			}
		}

		tx.commit();
		session.close();
		logger.info( "Entities saved");
		return savedEntities;
	}

	@SuppressWarnings("unchecked")
	public void saveAccumulativeDeals(List list)throws HibernateException{
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Iterator it = list.iterator();
		int i = 0;
		while(it.hasNext()){
			i++;
			AccumulatedDeal accumulatedDeal = (AccumulatedDeal)it.next();
			List<AccumulatedDeal> deals = session.createQuery("from AccumulatedDeal where orderingCurrency=:orderingCurrency")
					.setParameter("orderingCurrency", accumulatedDeal.getOrderingCurrency()).list();
			if(deals.size() > 0){
				AccumulatedDeal deal = deals.get(0);
				deal.setCount(deal.getCount().add(accumulatedDeal.getCount()));
				session.saveOrUpdate(deal);
			}
			else{
				session.persist(accumulatedDeal);
			}


			if (i % batchSize == 0) { session.flush(); session.clear(); }
		}
		tx.commit();
		session.close();
	}


}