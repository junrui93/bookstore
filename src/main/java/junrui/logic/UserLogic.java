package junrui.logic;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import junrui.mapper.*;
import junrui.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Component
public class UserLogic {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VerificationMapper verificationMapper;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PublicationMapper publicationMapper;

    @Autowired
    private PublPersonMapper publPersonMapper;

    @Autowired
    private VenueMapper venueMapper;

    @Transactional
    public boolean register(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null) {
            return false;
        }
        userMapper.insert(user);
        int userId = user.getId();
        String uuid = UUID.randomUUID().toString();
        Verification verification = new Verification();
        verification.setUserId(userId);
        verification.setCode(uuid);
        verificationMapper.insert(verification);
        String verificationUrl = String.format("http://localhost:8080/verify?id=%d&code=%s", userId, uuid);
        Utils.sendEmail(user.getEmail(), "Confirm Registration", "click the link to confirm: " + verificationUrl);
        return true;
    }

    public boolean verify(int userId, String code) {
        Verification verification = verificationMapper.selectByUserIdAndCode(userId, code);
        if (verification == null) {
            return false;
        }
        verificationMapper.deleteByPrimaryKey(verification.getId());
        return true;
    }

    @Transactional
    public boolean publicationAdd(Publication publication, String[] authorNames) {
        publicationMapper.insert(publication);
        int publicationId = publication.getId();

        Venue venue = venueMapper.selectByName(publication.getVenue());
        int venueId;
        if (venue == null) {
            Venue newVenue = new Venue();
            newVenue.setName(publication.getVenue());
            venueMapper.insert(newVenue);
            venueId = newVenue.getId();
        } else {
            venueId = venue.getId();
        }
        publication.setVenueId(venueId);

        for (String authorName : authorNames) {
            Person query = new Person();
            query.setName(authorName);
            query.setType(0);
            Person author = personMapper.selectByNameAndType(query);
            int authorId;
            if (author == null) {
                personMapper.insert(query);
                authorId = query.getId();
            } else {
                authorId = author.getId();
            }
            PublPerson publPerson = new PublPerson();
            publPerson.setPublId(publicationId);
            publPerson.setPersonId(authorId);
            publPersonMapper.insert(publPerson);
        }
        return true;
    }
}
