DELETE FROM security.users_roles where user_id in (select id from security.users where username <> 'admin')
and user_id not in ( select distinct user_id FROM data_collect.snapshot_draft) 
and user_id not in (select distinct user_id FROM data_collect.snapshots_economics) 
and user_id not in (select distinct user_id FROM security.password_reset_token); 

DELETE FROM ps_network.users_applications where user_id in (select id from security.users where username <> 'admin')
and user_id not in ( select distinct user_id FROM data_collect.snapshot_draft) 
and user_id not in (select distinct user_id FROM data_collect.snapshots_economics) 
and user_id not in (select distinct user_id FROM security.password_reset_token); 

DELETE FROM security.users where username <> 'admin' 
and id not in ( select distinct user_id FROM data_collect.snapshot_draft) 
and id not in (select distinct user_id FROM data_collect.snapshots_economics) 
and id not in (select distinct user_id FROM security.password_reset_token); 

DELETE FROM ps_network.organizations where id not in (select distinct organization_id from ps_families.family) 
and id not in (select distinct organization_id from ps_network.surveys_organizations where organization_id is not null)
and id not in (select distinct organization_id from ps_network.users_applications);

DELETE FROM ps_network.applications where id not in (select distinct application_id from ps_families.family)
and id not in (select distinct application_id from ps_network.organizations)
and id not in (select distinct application_id from ps_network.surveys_organizations where application_id is not null)
and id not in (select distinct application_id from ps_network.users_applications);
