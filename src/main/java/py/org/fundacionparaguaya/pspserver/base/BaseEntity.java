package py.org.fundacionparaguaya.pspserver.base;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * Base Entity
 * 
 * @author Marcos Cespedes
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

}