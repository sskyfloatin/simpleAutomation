package simple.automation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.management.relation.Role;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderTests extends BaseTestWithOrder {

    @BeforeAll
    public void createOrder() {
        System.out.println("Create order");
    }

    @ParameterizedTest
//    @ValueSource(strings = {"manager", "manager of managers", "manager of manager of managers"})
//    @EnumSource(mode = EnumSource.Mode.MATCH_ALL, names = {".*WORKER"})
    @MethodSource("getRoles")
    public void testListOrdersManager(Roles role) {
        System.out.println("List orders as " + role.getDescription());
        System.out.println("check that order is displayed");
    }

    static Stream<Roles> getRoles() {
        return Stream.of(Roles.MANAGER, Roles.LEAD_MANAGER);
    }

    enum Roles {
        MANAGER("manager"),
        SENIOR_MANAGER("manager of managers"),
        LEAD_MANAGER("manager of manager of managers"),
        HARD_WORKER("just a worker");

        Roles(String description) {
            this.description = description;
        }

        private String description;
        private String getDescription() {
            return this.description;
        }
    }

    @Test
    public void testCreateOrder() {
        System.out.println("Check test order was created");
    }

}
