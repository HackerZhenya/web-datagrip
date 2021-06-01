package com.hackerzhenya.datagrip;

import com.hackerzhenya.datagrip.models.Column;
import com.hackerzhenya.datagrip.models.Table;
import com.hackerzhenya.datagrip.requests.SignUpRequest;
import com.hackerzhenya.datagrip.services.ConnectionService;
import com.hackerzhenya.datagrip.services.TableService;
import com.hackerzhenya.datagrip.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("testing")
public class TableControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TableService tableService;

    @BeforeAll
    public static void addTestUser(@Autowired UserService userService) {
        var request = new SignUpRequest();

        request.setUsername("testing");
        request.setPassword("testing");

        userService.register(request);
    }

    @Test
    void getTables() throws Exception {
        var table = new Table("public", "test");

        Mockito.doReturn(List.of(table))
               .when(tableService)
               .getTables(UUID.fromString("00000000-0000-0000-0000-000000000000"));

        mvc.perform(get("/connections/00000000-0000-0000-0000-000000000000/tables"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType("text/html;charset=UTF-8"))
           .andExpect(xpath("/html/body/table/tbody/tr").nodeCount(1))
           .andExpect(xpath("/html/body/table/tbody/tr/td[1]").string(table.getSchema()))
           .andExpect(xpath("/html/body/table/tbody/tr/td[2]").string(table.getName()));
    }

    @Test
    void getRows() throws Exception {
        var table = new Table("public", "test", List.of(
                new Column("id", "uuid", false, 1),
                new Column("test", "varchar", true, 2),
                new Column("datetime", "timestamp", false, 3)
        ));

        Mockito.doReturn(Pair.of(table, List.of(
                new HashMap<String, String>() {{
                    put("id", UUID.randomUUID().toString());
                    put("test", "ООО \"Моя оборона\"");
                    put("datetime", Instant.now().toString());
                }},
                new HashMap<String, String>() {{
                    put("id", UUID.randomUUID().toString());
                    put("test", "JavaScript лучше чем Java (в нём магии меньше)");
                    put("datetime", Instant.now().toString());
                }}
        )))
               .when(tableService)
               .getRows(UUID.fromString("00000000-0000-0000-0000-000000000000"),
                       String.format("%s.%s", table.getSchema(), table.getName()),
                       1, 100);

        var result = mvc
                .perform(get(String.format("/connections/00000000-0000-0000-0000-000000000000/tables/%s.%s/rows",
                        table.getSchema(), table.getName())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));

        result.andExpect(xpath("/html/body/table/thead/tr/th").nodeCount(table.getColumns().size()));

        for (var column : table.getColumns()) {
            result.andExpect(xpath(String.format("/html/body/table/thead/tr/th[%d]", column.getPosition()))
                    .string(Matchers.startsWith(column.getName())));
        }
    }
}
