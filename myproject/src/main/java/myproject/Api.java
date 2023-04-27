package myproject;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Api {
	int result;
	String expretion;
	@PersistenceContext
	private EntityManager em;

	@POST
	@Path("calc")
	public Response calc(calculations c) {
		expretion = c.getOperation();
		switch (expretion) {
			case "+":
				result = c.getNumber1() + c.getNumber2();
				break;
			case "-":
				result = c.getNumber1() - c.getNumber2();
				break;
			case "*":
				result = c.getNumber1() * c.getNumber2();
				break;
			case "/":
				result = c.getNumber1() / c.getNumber2();
				break;
			default:
				return Response.status(400).entity("very bad").build();
		}

		em.persist(c);

		return Response.ok().entity("the result is " + result).build();
	}

	@GET
	@Path("calculations")
	public Response getresponse() {
		List<calculations> c = em.createQuery("SELECT operation FROM calculations operation", calculations.class)
				.getResultList();

		return Response.ok().entity(c).build();
	}

}
