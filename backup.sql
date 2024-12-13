--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: app_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.app_user (
    user_id bigint NOT NULL,
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.app_user OWNER TO postgres;

--
-- Name: app_user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.app_user ALTER COLUMN user_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.app_user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: appointment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.appointment (
    appointment_id bigint NOT NULL,
    date_time character varying(255),
    status character varying(255),
    doctor_id bigint,
    patient_id bigint
);


ALTER TABLE public.appointment OWNER TO postgres;

--
-- Name: appointment_appointment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.appointment ALTER COLUMN appointment_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.appointment_appointment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: doctor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctor (
    doctor_id bigint NOT NULL,
    name character varying(255),
    room_number character varying(255),
    specialty character varying(255),
    surname character varying(255),
    user_id bigint
);


ALTER TABLE public.doctor OWNER TO postgres;

--
-- Name: doctor_doctor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.doctor ALTER COLUMN doctor_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.doctor_doctor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: medical_record; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medical_record (
    medical_record_id bigint NOT NULL,
    date character varying(255),
    diagnosis character varying(255),
    treatment character varying(255),
    patient_id bigint
);


ALTER TABLE public.medical_record OWNER TO postgres;

--
-- Name: medical_record_medical_record_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.medical_record ALTER COLUMN medical_record_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.medical_record_medical_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: medication; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medication (
    medication_id bigint NOT NULL,
    description character varying(255),
    name character varying(255)
);


ALTER TABLE public.medication OWNER TO postgres;

--
-- Name: medication_medication_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.medication ALTER COLUMN medication_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.medication_medication_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: patient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient (
    patient_id bigint NOT NULL,
    address character varying(255),
    date_of_birth character varying(255),
    name character varying(255),
    phone_number character varying(255),
    surname character varying(255),
    user_id bigint
);


ALTER TABLE public.patient OWNER TO postgres;

--
-- Name: patient_patient_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.patient ALTER COLUMN patient_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.patient_patient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: prescription; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prescription (
    prescription_id bigint NOT NULL,
    dosage character varying(255),
    duration character varying(255),
    name character varying(255),
    medical_record_id bigint,
    medication_id bigint
);


ALTER TABLE public.prescription OWNER TO postgres;

--
-- Name: prescription_prescription_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.prescription ALTER COLUMN prescription_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.prescription_prescription_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: app_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.app_user (user_id, password, username) FROM stdin;
1	1	1
2	2	2
11	1111	user1
12	1111	user2
13	1111	user3
14	1111	doc1
15	1111	doc2
16	1111	doc3
17	1111	user4
18	111	user6
\.


--
-- Data for Name: appointment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.appointment (appointment_id, date_time, status, doctor_id, patient_id) FROM stdin;
19	Wed Dec 10 21:45:00 MSK 2025	Назначена	5	8
20	Thu Apr 10 21:46:00 MSK 2025	Назначена	5	9
22	Wed Jan 01 21:47:00 MSK 2025	Назначена	4	9
23	Thu Feb 13 21:48:00 MSK 2025	Назначена	6	10
24	Fri Jan 17 21:48:00 MSK 2025	Назначена	6	10
27	Sun Dec 08 21:50:00 MSK 2024	Закрыта	4	9
25	Sun Dec 08 21:49:00 MSK 2024	Закрыта	4	8
26	Sun Dec 08 21:49:00 MSK 2024	Закрыта	5	8
29	Sun Dec 08 21:50:00 MSK 2024	Закрыта	5	10
28	Mon Dec 09 21:50:00 MSK 2024	Закрыта	6	9
30	Sun Dec 08 22:04:00 MSK 2024	Закрыта	6	10
31	Mon Dec 09 22:04:00 MSK 2024	Закрыта	6	10
33	Wed Dec 11 21:45:00 MSK 2024	Назначена	4	8
34	Fri Jan 17 18:30:00 MSK 2025	Назначена	5	12
18	Wed Dec 11 21:45:00 MSK 2024	Закрыта	4	8
\.


--
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.doctor (doctor_id, name, room_number, specialty, surname, user_id) FROM stdin;
1	2	2	2	2	2
4	Аркадий	101	Хирург	Зиновьев	14
5	София	202	Офтальмолог	Андреевна	15
6	Петр	404	Психолог	Павлович	16
\.


--
-- Data for Name: medical_record; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medical_record (medical_record_id, date, diagnosis, treatment, patient_id) FROM stdin;
10	Wed Dec 11 21:45:00 MSK 2024	Сколиоз	ЛФК	8
11	Sun Dec 08 21:50:00 MSK 2024	Сложный перелом руки	Физиотерапия	9
12	Sun Dec 08 21:49:00 MSK 2024	Сколиоз	ЛФК	8
13	Sun Dec 08 21:49:00 MSK 2024	Дальнозоркость	Очки	8
14	Sun Dec 08 21:50:00 MSK 2024	Близорукость	Лазерная коррекция	10
15	Mon Dec 09 21:50:00 MSK 2024	Жалобы на семейную жизнь	Арт-терапия	9
16	Sun Dec 08 22:04:00 MSK 2024	Стресс	Отпуск	10
17	Mon Dec 09 22:04:00 MSK 2024	Стресс	Психоанализ	10
18	Wed Jan 01 21:47:00 MSK 2025	Диагноз 1	1	9
\.


--
-- Data for Name: medication; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medication (medication_id, description, name) FROM stdin;
10	Лечебная физическая культура	Прием 1
11	Физиотерапия — это раздел клинической медицины, который изучает физиологическое и лечебное действие природных и искусственно создаваемых физических факторов на организм человека.	Прием номер 2
12	Продолжаем курс лечения	Прием номер 2
13	-	Прием номер 1
14	Лазерная коррекция — это быстрая и безопасная процедура, позволяющая восстановить зрение при близорукости, дальнозоркости, в том числе – при их сочетании с астигматизмом. Несколько секунд бережного воздействия лазером — и вы увидите мир отчетливо и ярко.	Прием номер 2
15	Арт-терапия — направление в психотерапии и психологической коррекции, основанное на применении для терапии искусства и творчества.	Прием номер 1
16	Срочно уйти в отпуск	Прием номер 1
17	Подход направлен на изучение бессознательной части человека, выявление его внутренних конфликтов, детских переживаний и прошлого опыта, которые влияют на его психическое состояние, поведение и жизнь в целом. 	Прием номер 2
18	1	Лечение 1
\.


--
-- Data for Name: patient; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.patient (patient_id, address, date_of_birth, name, phone_number, surname, user_id) FROM stdin;
1	1	2019-04-06	1	1	1	1
9	Ростов	1978-05-03	Анатолий	+79216666666	Ефременко	12
10	Москва	2001-03-03	Ирина	+79216666677	Кабанова	13
11	Санкт-Петербург	2024-12-09	Александр	+79996667788	Николаев	17
8	Москва	1978-05-03	Василий	+79219999999	Скачков	11
12	Москва	2002-06-03	Алина	+79886608987	Усачева	18
\.


--
-- Data for Name: prescription; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.prescription (prescription_id, dosage, duration, name, medical_record_id, medication_id) FROM stdin;
14	1 шт. 2 раза в день.	2 недели	\N	11	11
15	3 капли 2 раза в день	1 месяц	\N	13	13
16	10 л.	1 р./д. 2 y недели	\N	15	15
17	1 т. 3 р./д.	3 недели	\N	15	15
18	-	Всю жизнь	\N	16	16
19	1 раз в 6 месяцев	4 года	\N	16	16
20	222	222	\N	18	18
\.


--
-- Name: app_user_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.app_user_user_id_seq', 18, true);


--
-- Name: appointment_appointment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.appointment_appointment_id_seq', 34, true);


--
-- Name: doctor_doctor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.doctor_doctor_id_seq', 6, true);


--
-- Name: medical_record_medical_record_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medical_record_medical_record_id_seq', 18, true);


--
-- Name: medication_medication_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medication_medication_id_seq', 18, true);


--
-- Name: patient_patient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.patient_patient_id_seq', 12, true);


--
-- Name: prescription_prescription_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.prescription_prescription_id_seq', 20, true);


--
-- Name: app_user app_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (user_id);


--
-- Name: appointment appointment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT appointment_pkey PRIMARY KEY (appointment_id);


--
-- Name: doctor doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (doctor_id);


--
-- Name: medical_record medical_record_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medical_record
    ADD CONSTRAINT medical_record_pkey PRIMARY KEY (medical_record_id);


--
-- Name: medication medication_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medication
    ADD CONSTRAINT medication_pkey PRIMARY KEY (medication_id);


--
-- Name: patient patient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT patient_pkey PRIMARY KEY (patient_id);


--
-- Name: prescription prescription_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription
    ADD CONSTRAINT prescription_pkey PRIMARY KEY (prescription_id);


--
-- Name: doctor uk3q0j5r6i4e9k3afhypo6uljph; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT uk3q0j5r6i4e9k3afhypo6uljph UNIQUE (user_id);


--
-- Name: patient uk6i3fp8wcdxk473941mbcvdao4; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT uk6i3fp8wcdxk473941mbcvdao4 UNIQUE (user_id);


--
-- Name: appointment fk4apif2ewfyf14077ichee8g06; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT fk4apif2ewfyf14077ichee8g06 FOREIGN KEY (patient_id) REFERENCES public.patient(patient_id);


--
-- Name: prescription fkf8hnlj7uxoroaf2mca0qyw4cx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription
    ADD CONSTRAINT fkf8hnlj7uxoroaf2mca0qyw4cx FOREIGN KEY (medical_record_id) REFERENCES public.medical_record(medical_record_id);


--
-- Name: prescription fkl4o7pf8dlgkvhux5s0cj4evv2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription
    ADD CONSTRAINT fkl4o7pf8dlgkvhux5s0cj4evv2 FOREIGN KEY (medication_id) REFERENCES public.medication(medication_id);


--
-- Name: patient fkocqalxvo9u3uxwggs6ay2qmpy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT fkocqalxvo9u3uxwggs6ay2qmpy FOREIGN KEY (user_id) REFERENCES public.app_user(user_id);


--
-- Name: appointment fkoeb98n82eph1dx43v3y2bcmsl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT fkoeb98n82eph1dx43v3y2bcmsl FOREIGN KEY (doctor_id) REFERENCES public.doctor(doctor_id);


--
-- Name: doctor fkqfmt6yrrw6gnryw87otq45b65; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT fkqfmt6yrrw6gnryw87otq45b65 FOREIGN KEY (user_id) REFERENCES public.app_user(user_id);


--
-- Name: medical_record fkt0lf3feuiurr73bpln2n6x0v; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medical_record
    ADD CONSTRAINT fkt0lf3feuiurr73bpln2n6x0v FOREIGN KEY (patient_id) REFERENCES public.patient(patient_id);


--
-- PostgreSQL database dump complete
--
