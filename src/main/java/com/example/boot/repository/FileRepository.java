package com.example.boot.repository;

import com.example.boot.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, String> {

    List<File> findByBno(Long bno);

    List<File> findBySaveDir(String today);
}