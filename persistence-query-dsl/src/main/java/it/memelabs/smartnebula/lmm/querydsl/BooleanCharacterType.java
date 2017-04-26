/*
 * Copyright 2015, The Querydsl Team (http://www.querydsl.com/team)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.memelabs.smartnebula.lmm.querydsl;

import com.querydsl.sql.types.AbstractType;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * {@code CharacterType} maps Character to Character on the JDBC level
 *
 * @author tiwe
 */
public class BooleanCharacterType extends AbstractType<Boolean> {
    private static final Logger LOG = getLogger(BooleanCharacterType.class);


    public BooleanCharacterType() {
        super(Types.CHAR);
    }

    public BooleanCharacterType(int type) {
        super(type);
    }

    @Override
    public Boolean getValue(ResultSet rs, int startIndex) throws SQLException {
        String str = rs.getString(startIndex);
        if (str == null) return null;
        else if ('Y' == str.charAt(0) || 'y' == str.charAt(0)) return true;
        else if ('N' == str.charAt(0) || 'n' == str.charAt(0)) return false;
        else {
            LOG.error("Invalid value", str);
            return null;
        }

    }

    @Override
    public Class<Boolean> getReturnedClass() {
        return Boolean.class;
    }

    @Override
    public void setValue(PreparedStatement st, int startIndex, Boolean value)
            throws SQLException {
        st.setString(startIndex, value != null ? (value ? "Y" : "N") : null);
    }

}
