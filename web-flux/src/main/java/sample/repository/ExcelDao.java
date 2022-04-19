package sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sample.data.ExcelFile;

public interface excelDao extends JpaRepository<ExcelFile, String>, JpaSpecificationExecutor<ExcelFile> {

}
