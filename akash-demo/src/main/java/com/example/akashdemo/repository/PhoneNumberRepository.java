package com.example.akashdemo.repository;

import com.example.akashdemo.entity.PhoneNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "phone", path = "phone")
public interface PhoneNumberRepository extends PagingAndSortingRepository<PhoneNumber, Long> {

    Page<PhoneNumber> findByPhoneNumber(@Param("phoneNumber") String phoneNumber, Pageable pageable);
}
