import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class ProjectOne
{
    public static void main(String... args)
    {
        // try block with resources that automatically close any resource opened during program execution.
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root",
                "playdirty");
            Statement statement = connection.createStatement())
        {
            //Call to the method createTable that creates a table with the following attributes; Name, Email, Age,
            // Location, Designation.
            createTable(statement);

            //Call to the method populateTable that populates a table with a set of values.
            int rows = populateTable(connection);

            System.out.printf("\nTable with %d rows Created!\n", rows);
        }
        //catch block that catches exceptions thrown by the createTable and populateTable methods.
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * createTable - creates a Table with attributes; name, email, age, location, designation.
     *
     * Return: void
     */
    public static void createTable(Statement statement) throws SQLException
    {
            statement.execute("CREATE TABLE IF NOT EXISTS UserData(name Text not null, email Text not null, age Integer, location Text," +
                    " Designation Text)");
            System.out.println("\nTable created Successfully!\n");
    }

    /**
     * populateTable - populate a table  already created with rows.
     *
     * @rowCount: number of rows added into table
     *
     * Return: @rowCount
     */
    public static int populateTable(Connection connection) throws SQLException
    {
        int rowCount = 0;

        try (Scanner input = new Scanner(System.in);
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO UserData values(?, ?, ?, ?, ?)"))
        {
            for (int count = 0; count < 10; count++) {
                System.out.print("\nInput Name, Email, Age, Location and Designation separated by single space: ");
                String name = input.next();
                String email = input.next();
                int age = input.nextInt();
                String location = input.next();
                String designation = input.next();

                insertStatement.setString(1, name);
                insertStatement.setString(2, email);
                insertStatement.setInt(3, age);
                insertStatement.setString(4, location);
                insertStatement.setString(5, designation);

                rowCount += insertStatement.executeUpdate();
            }
        }
        System.out.println("\nTable Successfully populated with values!\n");
        return rowCount;
    }
}
