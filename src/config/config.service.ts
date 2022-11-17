import { Injectable } from "@nestjs/common";
import { TypeOrmModuleOptions } from "@nestjs/typeorm";
import { User } from "src/users/user.entity";

@Injectable()
export class ConfigService {
	public getValue(key: string, defaultValue?: string): string {
		const value = process.env[key];
		if (!value && !defaultValue) {
			throw new Error(`type orm config error: missing env.${key}`);
		}

		return value || defaultValue;
	}

	public getTypeOrmConfig(): TypeOrmModuleOptions {
		return {
			type: "postgres",
			host: this.getValue("POSTGRES_HOST", "0.0.0.0"),
			port: parseInt(this.getValue("POSTGRES_PORT", "5432")),
			username: this.getValue("POSTGRES_USER"),
			password: this.getValue("POSTGRES_PASSWORD"),
			database: this.getValue("POSTGRES_DATABASE"),
			entities: [User],
			migrationsTableName: "migrations",
			migrations: ["src/migration/*.ts"],
			synchronize: true
		};
	}
}
