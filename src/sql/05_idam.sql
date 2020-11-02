alter table license add column user_id varchar(36);
COMMENT ON COLUMN license.user_id IS 'The Keycloak User Id who owns the license';

alter table license add column license_category varchar(20);
COMMENT ON COLUMN license.license_category IS 'The category of license.  Currently only Technican is supported.';

alter table license add column license_sub_category varchar(20);
COMMENT ON COLUMN license.license_sub_category IS 'The further categorizatin of the license. (eg: Full, Temporary, Guest)';

alter table license add column pending_application boolean;
COMMENT ON COLUMN license.pending_application IS 'Indicates if a license as a pending application associated with it.';