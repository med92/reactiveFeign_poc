package sample.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "excel_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ExcelFile {
    @Id
    private String id;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column
    private int age;

    @Column
    private String numtel;

}
