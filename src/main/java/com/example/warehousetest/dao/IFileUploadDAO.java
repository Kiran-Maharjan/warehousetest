package com.example.warehousetest.dao;

import com.example.warehousetest.model.AccumulatedDeal;
import com.example.warehousetest.model.InValidDeal;
import com.example.warehousetest.model.ValidDeal;

import java.util.Collection;
import java.util.List;

public interface IFileUploadDAO {

	boolean fileExists(String fileName);

	<T extends ValidDeal> Collection<T> bulkValidSave(Collection<T> entities, List<AccumulatedDeal> accumulatedDeals);

	<T extends InValidDeal> Collection<T> bulkInvalidSave(Collection<T> entities);
}
