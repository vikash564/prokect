package com.observer.pattern.service;

import com.observer.pattern.model.Locker;
import org.springframework.stereotype.Service;

@Service
public interface DBService {
    Locker insert(Locker locker);
    Locker update(Locker locker);
    void delete(Locker locker);
    String getLocker(Long lockerId);
    String bulkInsert();
}
