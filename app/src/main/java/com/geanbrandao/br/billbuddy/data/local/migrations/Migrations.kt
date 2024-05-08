package com.geanbrandao.br.billbuddy.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE users RENAME COLUMN id TO userId")
            db.execSQL("ALTER TABLE items RENAME COLUMN id TO itemId")
            db.execSQL("ALTER TABLE bills ADD COLUMN status TEXT NOT NULL DEFAULT 'NEW'")
            db.execSQL("ALTER TABLE items DROP COLUMN userId")
            db.execSQL("""
                CREATE TABLE user_items_cross_ref (
                    userId INTEGER NOT NULL,
                    itemId INTEGER NOT NULL,
                    dividedValue REAL NOT NULL,
                    PRIMARY KEY (userId, itemId),
                    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE,
                    FOREIGN KEY (itemId) REFERENCES items (itemId) ON DELETE CASCADE
                )
            """)
            db.execSQL("CREATE INDEX index_user_items_cross_ref_userId ON user_items_cross_ref(userId)")
            db.execSQL("CREATE INDEX index_user_items_cross_ref_itemId ON user_items_cross_ref(itemId)")
        }
    }
}
