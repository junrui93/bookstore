package junrui.logic;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<String, Object> calculatePages(int resultSize, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();

        int maxPage = (resultSize + pageSize - 1) / 10;
        page = Math.min(page, maxPage);
        page = Math.max(page, 1);
        int leftPage = Math.max(page - pageSize / 2, 1);
        int rightPage = Math.min(leftPage + pageSize - 1, maxPage);

        map.put("resultSize", resultSize);
        map.put("maxPage", maxPage);
        map.put("page", page);
        map.put("leftPage", leftPage);
        map.put("rightPage", rightPage);

        return map;
    }

    public static ClientResponse sendEmail(String toAddress, String mailTitle, String mailText) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", "key-7f096c1132b32e8b62fbee5d0cb43b6f"));
        WebResource webResource = client.resource("https://api.mailgun.net/v3/mg.chenjr.cc/messages");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "noreply <postmaster@mg.chenjr.cc>");
        formData.add("to", toAddress);
        formData.add("subject", mailTitle);
        formData.add("text", mailText);
        return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
    }
}
