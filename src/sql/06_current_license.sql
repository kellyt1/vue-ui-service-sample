DROP VIEW license_current;
CREATE VIEW
    license_current
    (
        id,
        NUMBER,
        license_type,
        status,
        effective_date,
        expiration_date,
        date_of_birth,
        renewal_token,
        first_name,
        middle_name,
        last_name,
        street_address,
        city,
        state,
        postal_code,
        address_type,
        preferred,
        personal_phone,
        work_phone,
        mobile_phone,
        email_address,
        user_id,
        license_category,
        license_sub_category,
        pending_application,
        mn_supervisor,
        other_names,
        discipline,
        discipline_link,
        discipline_name
    ) AS
SELECT
    a.id,
    a.number,
    a.license_type,
    a.status,
    a.effective_date,
    a.expiration_date,
    a.date_of_birth,
    a.renewal_token,
    b.first_name,
    b.middle_name,
    b.last_name,
    c.street_address,
    c.city,
    c.state,
    c.postal_code,
    c.address_type,
    c.preferred,
    b.personal_phone,
    b.work_phone,
    b.mobile_phone,
    b.email_address,
    a.user_id,
    a.license_category,
    a.license_sub_category,
    a.pending_application,
    string_agg(distinct(s.name || ' - ' || s.license_nbr), ', ') FILTER (WHERE s.name is not null) as mn_supervisor,
    string_agg(distinct(n.first_name || ' ' || n.middle_name || ' ' || n.last_name), ', ') FILTER (WHERE n.first_name is not null) as other_names,
    case when d.response = 'true' then 'Yes' else 'No' end as discipline,
    l.url as discipline_link,
    l.file_name as discipline_name
FROM
    ((license a
LEFT JOIN
    application b
ON
    ((((
                    b.license_id)::text = (a.id)::text)
        AND ((
                    b.status)::text = 'APPROVED'::text)
        AND (
                b.received_date =
                (
                    SELECT
                        MAX(b1.received_date) AS MAX
                    FROM
                        application b1
                    WHERE
                        (((
                                    b1.license_id)::text = (a.id)::text)
                        AND ((
                                    b1.status)::text = 'APPROVED'::text)))))))
LEFT JOIN
    address c
ON
    ((((
                    b.id)::text = (c.application_id)::text)
        AND (
                c.preferred = true))))
LEFT JOIN
    mn_supervisor s
ON ((s.application_id)::text = (b.id)::text)
LEFT JOIN
    other_names n
ON ((n.application_id)::text = (b.id)::text)
LEFT JOIN
    question_response d
ON ((d.application_id)::text = (b.id)::text and d.question_code = '093d2e01-9430-4199-9090-083be41b1b1e')
LEFT JOIN
    attachment l
ON ((l.application_id)::text = (b.id)::text and l.attachment_type = 'DISCIPLINE')
GROUP BY a.id, b.id, c.id, d.response, l.id;

COMMENT ON VIEW license_current
IS
    'Renders the current demographic information associated with a license - prepopulate renewal form'
    ;

GRANT SELECT ON license_current TO bodyart_user;
