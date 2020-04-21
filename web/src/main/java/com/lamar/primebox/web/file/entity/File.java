package com.lamar.primebox.web.file.entity;

import com.lamar.primebox.web.auth.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "file_id")
    private String fileID;
    @Column(name = "filename")
    private String filename;
    @Column(name = "type")
    private String type;
    @Column(name = "size")
    private long size;
    @Column(name = "last_modified")
    private long lastModified;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    public File(String filename, String type, int size, long lastModified) {
        this.filename = filename;
        this.type = type;
        this.size = size;
        this.lastModified = lastModified;
    }

}
