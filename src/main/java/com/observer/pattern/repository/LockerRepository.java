package com.observer.pattern.repository;

import com.observer.pattern.entity.LockerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LockerRepository extends JpaRepository<LockerEntity, Long> {

    LockerEntity findByLockerId(Long lockerId);

    @Modifying
    @Query("UPDATE LockerEntity l SET l.status='NA' WHERE l.lockerId=?1 and l.doorId=?2 and l.status =?3")
    int updateLocker(Long lockerId , Long doorId , String status);
}
