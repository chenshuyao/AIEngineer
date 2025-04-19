package com.aiengineer.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @NotBlank(message = "姓名不能为空")
    @Size(max = 64, message = "姓名长度不能超过64个字符")
    private String name;

    @NotBlank(message = "性别不能为空")
    @Size(max = 8, message = "性别长度不能超过8个字符")
    private String gender;

    @NotBlank(message = "电话不能为空")
    @Size(max = 16, message = "电话长度不能超过16个字符")
    private String phone;

    @NotNull(message = "年龄不能为空")
    private Integer age;

    @Size(max = 64, message = "籍贯长度不能超过64个字符")
    private String nativePlace;

    @NotBlank(message = "专业不能为空")
    @Size(max = 128, message = "专业长度不能超过128个字符")
    private String major;

    @Email(message = "邮箱格式不正确")
    @Size(max = 32, message = "邮箱长度不能超过32个字符")
    private String email;

    @Size(max = 512, message = "标签长度不能超过512个字符")
    private String tag;

    @Size(max = 512, message = "备注长度不能超过512个字符")
    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    private Integer isDelete = 0;

    private Long creator;

    @PrePersist
    public void prePersist() {
        createTime = LocalDateTime.now();
        modifyTime = LocalDateTime.now();
        isDelete = 0;
    }

    @PreUpdate
    public void preUpdate() {
        modifyTime = LocalDateTime.now();
    }
}
