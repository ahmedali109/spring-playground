package org.example.booting.data.repository;
import org.example.booting.data.entity.EmployeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface EmployeeRepository extends JpaRepository<EmployeesEntity , UUID> {}
