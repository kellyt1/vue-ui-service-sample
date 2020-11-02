--grants for bodyart_user
GRANT USAGE ON SCHEMA bodyart_owner to bodyart_user;

GRANT SELECT, INSERT, UPDATE, DELETE ON application TO bodyart_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON employer TO bodyart_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON attachment TO bodyart_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON question_response TO bodyart_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON question TO bodyart_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON payment TO bodyart_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON deposit TO bodyart_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON license TO bodyart_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON other_names TO bodyart_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON mn_supervisor TO bodyart_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON address TO bodyart_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON application_property TO bodyart_user;
