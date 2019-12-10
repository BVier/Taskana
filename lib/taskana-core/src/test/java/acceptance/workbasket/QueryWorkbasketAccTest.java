package acceptance.workbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pro.taskana.WorkbasketQueryColumnName.NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import acceptance.AbstractAccTest;
import pro.taskana.BaseQuery.SortDirection;
import pro.taskana.WorkbasketPermission;
import pro.taskana.WorkbasketQuery;
import pro.taskana.WorkbasketService;
import pro.taskana.WorkbasketSummary;
import pro.taskana.WorkbasketType;
import pro.taskana.exceptions.InvalidArgumentException;
import pro.taskana.exceptions.NotAuthorizedException;
import pro.taskana.security.JAASExtension;
import pro.taskana.security.WithAccessId;

/**
 * Acceptance test for all "query workbasket by permission" scenarios.
 */
@ExtendWith(JAASExtension.class)
class QueryWorkbasketAccTest extends AbstractAccTest {

    private static SortDirection asc = SortDirection.ASCENDING;
    private static SortDirection desc = SortDirection.DESCENDING;

    QueryWorkbasketAccTest() {
        super();
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1_1"})
    @Test
    void testQueryAllForUserMultipleTimes() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        WorkbasketQuery query = workbasketService.createWorkbasketQuery();
        long count = query.count();
        assertEquals(4, count);
        List<WorkbasketSummary> workbaskets = query.list();
        assertNotNull(workbaskets);
        assertEquals(count, workbaskets.size());
        workbaskets = query.list();
        assertNotNull(workbaskets);
        assertEquals(count, workbaskets.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"businessadmin"})
    @Test
    void testQueryAllForBusinessAdminMultipleTimes() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        WorkbasketQuery query = workbasketService.createWorkbasketQuery();
        long count = query.count();
        assertEquals(25, count);
        List<WorkbasketSummary> workbaskets = query.list();
        assertNotNull(workbaskets);
        assertEquals(count, workbaskets.size());
        workbaskets = query.list();
        assertNotNull(workbaskets);
        assertEquals(count, workbaskets.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"admin"})
    @Test
    void testQueryAllForAdminMultipleTimes() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        WorkbasketQuery query = workbasketService.createWorkbasketQuery();
        long count = query.count();
        assertEquals(25, count);
        List<WorkbasketSummary> workbaskets = query.list();
        assertNotNull(workbaskets);
        assertEquals(count, workbaskets.size());
        workbaskets = query.list();
        assertNotNull(workbaskets);
        assertEquals(count, workbaskets.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketValuesForColumnName() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<String> columnValueList = workbasketService.createWorkbasketQuery()
            .listValues(NAME, null);
        assertNotNull(columnValueList);
        assertEquals(10, columnValueList.size());

        columnValueList = workbasketService.createWorkbasketQuery()
            .nameLike("%korb%")
            .orderByName(asc)
            .listValues(NAME, SortDirection.DESCENDING);  // will override
        assertNotNull(columnValueList);
        assertEquals(4, columnValueList.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByDomain() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .domainIn("DOMAIN_B")
            .list();
        assertEquals(1L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByDomainAndType() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .domainIn("DOMAIN_A")
            .typeIn(WorkbasketType.PERSONAL)
            .list();
        assertEquals(6L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByName() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .nameIn("Gruppenpostkorb KSC")
            .list();
        assertEquals(1L, results.size());
        assertEquals("GPK_KSC", results.get(0).getKey());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByNameStartsWith() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .nameLike("%Gruppenpostkorb KSC%")
            .list();
        assertEquals(3L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByNameContains() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .nameLike("%Teamlead%", "%Gruppenpostkorb KSC%")
            .list();
        assertEquals(5L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByNameContainsCaseInsensitive() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .nameLike("%TEAMLEAD%")
            .list();
        assertEquals(2L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByDescription() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .descriptionLike("%ppk%", "%gruppen%")
            .orderByType(desc)
            .orderByDescription(asc)
            .list();
        assertEquals(9L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByOwnerLike() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .ownerLike("%an%", "%te%")
            .orderByOwner(asc)
            .list();
        assertEquals(1L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByKey() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .keyIn("GPK_KSC")
            .list();
        assertEquals(1L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByMultipleKeys() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .keyIn("GPK_KSC_1", "GPK_KSC")
            .list();
        assertEquals(2L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByMultipleKeysWithUnknownKey() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .keyIn("GPK_KSC_1", "GPK_Ksc", "GPK_KSC_3")
            .list();
        assertEquals(2L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByKeyContains() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .keyLike("%KSC%")
            .list();
        assertEquals(3L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByKeyContainsIgnoreCase() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .keyLike("%kSc%")
            .list();
        assertEquals(3L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByKeyOrNameContainsIgnoreCase() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .keyOrNameLike("%kSc%")
            .list();
        assertEquals(9L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByNameStartsWithSortedByNameAscending() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .nameLike("%Gruppenpostkorb KSC%")
            .orderByName(asc)
            .list();
        assertEquals(3L, results.size());
        assertEquals("GPK_KSC", results.get(0).getKey());

        // check sort order is correct
        WorkbasketSummary previousSummary = null;
        for (WorkbasketSummary wbSummary : results) {
            if (previousSummary != null) {
                assertTrue(wbSummary.getName().compareToIgnoreCase(
                    previousSummary.getName()) >= 0);
            }
            previousSummary = wbSummary;
        }

    }

    @WithAccessId(
        userName = "max")
    @Test
    void testQueryWorkbasketByNameStartsWithSortedByNameDescending() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .nameLike("basxet%")
            .orderByName(desc)
            .list();
        assertEquals(10L, results.size());
        // check sort order is correct
        WorkbasketSummary previousSummary = null;
        for (WorkbasketSummary wbSummary : results) {
            if (previousSummary != null) {
                assertTrue(wbSummary.getName().compareToIgnoreCase(
                    previousSummary.getName()) <= 0);
            }
            previousSummary = wbSummary;
        }
    }

    @WithAccessId(
        userName = "max")
    @Test
    void testQueryWorkbasketByNameStartsWithSortedByKeyAscending() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .nameLike("basxet%")
            .orderByKey(asc)
            .list();
        assertEquals(10L, results.size());
        // check sort order is correct
        WorkbasketSummary previousSummary = null;
        for (WorkbasketSummary wbSummary : results) {
            if (previousSummary != null) {
                assertTrue(wbSummary.getKey().compareToIgnoreCase(
                    previousSummary.getKey()) >= 0);
            }
            previousSummary = wbSummary;
        }
    }

    @WithAccessId(
        userName = "max")
    @Test
    void testQueryWorkbasketByNameStartsWithSortedByKeyDescending() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .nameLike("basxet%")
            .orderByKey(desc)
            .list();
        assertEquals(10L, results.size());
        // check sort order is correct
        WorkbasketSummary previousSummary = null;
        for (WorkbasketSummary wbSummary : results) {
            if (previousSummary != null) {
                assertTrue(wbSummary.getKey().compareToIgnoreCase(
                    previousSummary.getKey()) <= 0);
            }
            previousSummary = wbSummary;
        }
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByCreated() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .createdWithin(todaysInterval())
            .list();
        assertEquals(9L, results.size());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryWorkbasketByModified() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .modifiedWithin(todaysInterval())
            .list();
        assertEquals(9L, results.size());
    }

    @WithAccessId(
        userName = "unknown",
        groupNames = "admin")
    @Test
    void testQueryWorkbasketByAdmin()
        throws NotAuthorizedException, InvalidArgumentException {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .nameLike("%")
            .orderByName(desc)
            .list();
        assertEquals(25L, results.size());
        // check sort order is correct
        WorkbasketSummary previousSummary = null;
        for (WorkbasketSummary wbSummary : results) {
            if (previousSummary != null) {
                assertTrue(wbSummary.getName().compareToIgnoreCase(
                    previousSummary.getName()) <= 0);
            }
            previousSummary = wbSummary;
        }

        results = workbasketService.createWorkbasketQuery()
            .nameLike("%")
            .accessIdsHavePermission(WorkbasketPermission.TRANSFER, "teamlead_1", "group_1", "group_2")
            .orderByName(desc)
            .list();

        assertEquals(13L, results.size());

    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = "group_1")
    @Test
    void testQueryWorkbasketByDomainLike() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .domainLike("DOMAIN_%").orderByDomain(asc).list();

        ArrayList<String> expectedIds = new ArrayList<>(
            Arrays.asList("WBI:100000000000000000000000000000000001", "WBI:100000000000000000000000000000000002",
                "WBI:100000000000000000000000000000000004", "WBI:100000000000000000000000000000000005",
                "WBI:100000000000000000000000000000000006", "WBI:100000000000000000000000000000000007",
                "WBI:100000000000000000000000000000000008", "WBI:100000000000000000000000000000000009",
                "WBI:100000000000000000000000000000000010", "WBI:100000000000000000000000000000000012"));
        assertEquals(10L, results.size());
        for (String id : expectedIds) {
            assertTrue(results.stream().anyMatch(wbSummary -> wbSummary.getId().equals(id)));
        }
    }

    @WithAccessId(
        userName = "admin",
        groupNames = "group_1")
    @Test
    void testQueryWorkbasketByOwnerInOrderByDomainDesc() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .ownerIn("owner0815").orderByDomain(desc).list();

        assertEquals(2L, results.size());
        assertEquals("WBI:100000000000000000000000000000000015", results.get(0).getId());
        assertEquals("WBI:100000000000000000000000000000000001", results.get(1).getId());
    }

    @WithAccessId(
        userName = "teamlead_1",
        groupNames = {"group_1"})
    @Test
    void testQueryForCustom1In() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .custom1In("ABCQVW").list();

        assertEquals(1, results.size());
        assertEquals("WBI:100000000000000000000000000000000001", results.get(0).getId());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForCustom1Like() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .custom1Like("custo%")
            .list();
        assertEquals(2, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForCustom2In() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .custom2In("cust2", "custom2")
            .list();
        assertEquals(3, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForCustom2Like() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .custom2Like("cusTo%")
            .list();
        assertEquals(3, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForCustom3In() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .custom3In("custom3")
            .list();
        assertEquals(2, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForCustom3Like() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .custom3Like("cu%")
            .list();
        assertEquals(3, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForCustom4In() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .custom4In("custom4", "team")
            .list();
        assertEquals(3, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForCustom4Like() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .custom4Like("%u%")
            .list();
        assertEquals(5, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrgLevl1In() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orgLevel1In("orgl1", "")
            .list();
        assertEquals(24, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrgLevel1Like() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orgLevel1Like("%1")
            .list();
        assertEquals(2, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrgLevel2In() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orgLevel2In("abteilung")
            .list();
        assertEquals(1, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrgLevel2Like() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orgLevel2Like("ab%")
            .list();
        assertEquals(1, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrgLevel3In() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orgLevel3In("orgl3")
            .list();
        assertEquals(2, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrgLevel3Like() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orgLevel3Like("org%")
            .list();
        assertEquals(2, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrgLevel4In() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orgLevel4In("team", "orgl4")
            .list();
        assertEquals(2, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrgLevel4Like() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orgLevel4Like("%")
            .list();
        assertEquals(25, results.size());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrderByOrgLevel1Desc() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orderByOrgLevel1(desc)
            .list();
        assertEquals("WBI:100000000000000000000000000000000007", results.get(0).getId());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrderByOrgLevel2Asc() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orderByOrgLevel2(asc)
            .list();
        assertEquals("WBI:100000000000000000000000000000000007", results.get(results.size() - 3).getId());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrderByOrgLevel3Desc() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orderByOrgLevel3(desc)
            .list();
        assertEquals("WBI:100000000000000000000000000000000007", results.get(0).getId());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrderByOrgLevel4Asc() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orderByOrgLevel4(asc)
            .list();
        assertEquals("WBI:100000000000000000000000000000000012", results.get(results.size() - 3).getId());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrderByCustom1Asc() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orderByCustom1(asc)
            .list();
        assertEquals("WBI:100000000000000000000000000000000015", results.get(results.size() - 4).getId());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrderByCustom2Desc() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orderByCustom2(desc)
            .list();
        assertEquals("WBI:100000000000000000000000000000000014", results.get(0).getId());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrderByCustom3Asc() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orderByCustom3(asc)
            .list();
        assertEquals("WBI:100000000000000000000000000000000015", results.get(results.size() - 4).getId());
    }

    @WithAccessId(
        userName = "admin")
    @Test
    void testQueryForOrderByCustom4Desc() {
        WorkbasketService workbasketService = taskanaEngine.getWorkbasketService();
        List<WorkbasketSummary> results = workbasketService.createWorkbasketQuery()
            .orderByCustom4(desc)
            .list();
        assertEquals("WBI:100000000000000000000000000000000006", results.get(0).getId());
    }

}
