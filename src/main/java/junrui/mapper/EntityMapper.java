package junrui.mapper;

import java.util.List;

import junrui.model.Entity;
import junrui.model.Person;
import junrui.model.Publication;
import junrui.model.Venue;

public interface EntityMapper {
	List<Publication> selectedAttributes();
	List<Person> selectAllPersons();
	List<Venue> selectVenues();
	int insertPub(Entity entry);
	int insertPerson(Entity entry);
	int insertVenue(Entity entry);
}
