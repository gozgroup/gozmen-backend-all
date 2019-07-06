package com.gozmen.ucenter.repository;

import com.gozmen.ucenter.domain.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findUserByName(String name);

}
