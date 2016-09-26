package junrui.mapper;

import java.util.List;

import junrui.model.Entity;
import junrui.model.Publication;

public interface EntityMapper {
	List<Publication> selectedAttributes();
	int insert(Entity entry);
}
