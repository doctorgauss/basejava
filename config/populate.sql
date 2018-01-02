INSERT INTO resume (uuid, full_name) VALUES
  ('731c2a92-a247-44f3-b30c-f12f80c4bb26', 'Name1'),
  ('a92dad8e-c777-4420-ab4d-2063539e84c9', 'Name2'),
  ('35235020-7baf-46fa-b72f-29cebb512e09', 'Name3');
INSERT INTO contact (resume_uuid, type, value) VALUES
  ('731c2a92-a247-44f3-b30c-f12f80c4bb26', 'phone', '12345'),
  ('731c2a92-a247-44f3-b30c-f12f80c4bb26', 'skype', 'myskype');