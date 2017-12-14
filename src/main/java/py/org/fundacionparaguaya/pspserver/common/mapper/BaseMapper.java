package py.org.fundacionparaguaya.pspserver.common.mapper;

import java.util.List;

public interface BaseMapper<E, D> {

	public List<D> entityListToDtoList(List<E> entityList);
	
	public D entityToDto(E entity);
	
	public E dtoToEntity(D dto);
	
}
