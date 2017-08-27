package py.org.fundacionparaguaya.pspserver.common.services;

import java.util.List;

public interface BaseService<E> {

	public List<E> convertToDtoList(List<E> list, Class c);
	
	public E convertToDto(E entity, Class c);
	
	public E convertToEntity(E entity, Class c);
	
}
