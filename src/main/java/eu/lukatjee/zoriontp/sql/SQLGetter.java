package eu.lukatjee.zoriontp.sql;

import eu.lukatjee.zoriontp.ZorionTP;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SQLGetter {

    private ZorionTP plugin;

    public SQLGetter(ZorionTP plugin) {

        this.plugin = plugin;

    }

    public void createTable() {

        PreparedStatement ps;

        try {

            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS zoriontp_db (uuid VARCHAR(45), warp VARCHAR(45), world VARCHAR(45), x VARCHAR(45), y VARCHAR(45), z VARCHAR(45)) COLLATE latin1_general_cs;");
            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void createWarp(UUID uuid, String key, String world, Integer x, Integer y, Integer z) {

        PreparedStatement ps;

        try {

            ps = plugin.SQL.getConnection().prepareStatement("INSERT INTO zoriontp_db (uuid, warp, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?);");
            ps.setString(1, uuid.toString());
            ps.setString(2, key);
            ps.setString(3, world);
            ps.setString(4, x.toString());
            ps.setString(5, y.toString());
            ps.setString(6, z.toString());

            ps.execute();


        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public List<Object> readWarp(String warpKey) {

        PreparedStatement ps;

        try {

            ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM zoriontp_db WHERE ? IN (warp);");
            ps.setString(1, warpKey);

            ResultSet result = ps.executeQuery();

            if (result != null && result.next()) {

                UUID uuid = UUID.fromString(result.getString("uuid"));
                String warp = result.getString("warp");
                String world = result.getString("world");
                Integer x = Integer.parseInt(result.getString("x"));
                Integer y = Integer.parseInt(result.getString("y"));
                Integer z = Integer.parseInt(result.getString("z"));

                return Arrays.asList(uuid, warp, world, x, y, z);

            } else {

                return null;

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;
    }

    public void removeWarp(String warpKey) {

        PreparedStatement ps;

        try {

            ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM zoriontp_db WHERE warp=?;");
            ps.setString(1, warpKey);

            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}
