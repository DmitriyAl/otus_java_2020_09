package otus.java.tomcat.service;

import otus.java.tomcat.model.HasDto;

import java.util.List;

public interface DbService<DTO, ENTITY extends HasDto<DTO>, ID> {

    ID save(ENTITY entity);

    DTO getById(ID id);

    List<DTO> getAll();
}