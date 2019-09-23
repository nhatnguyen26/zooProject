package com.zoo.zooApplication.dao.repository;

import com.zoo.zooApplication.dao.model.PriceChartDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceChartRepository extends JpaRepository<PriceChartDO, Long> {
}
