package com.busience.common.persistence;

import org.springframework.data.repository.CrudRepository;

import com.busience.common.domain.Menu;

public interface MenuRepository extends CrudRepository<Menu, Long> {

}
