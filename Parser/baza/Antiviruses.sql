--
-- PostgreSQL database dump
--

-- Dumped from database version 10.3
-- Dumped by pg_dump version 10.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: antivirus_software; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.antivirus_software (
    id integer NOT NULL,
    name text,
    version_of_data integer,
    date text,
    firewall text,
    malware text,
    "parental control" text,
    "http monitor" text,
    ransomware text,
    backup text,
    browsing text,
    "real-time" text,
    "anti-spam" text,
    password text,
    "wi-fi security" text,
    "data loss prevention" text
);


--
-- Data for Name: antivirus_software; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.antivirus_software (id, name, version_of_data, date, firewall, malware, "parental control", "http monitor", ransomware, backup, browsing, "real-time", "anti-spam", password, "wi-fi security", "data loss prevention") FROM stdin;
5	F-Secure	1	19.05.2018	T	T	T	NA	T	NA	T	T	NA	T	NA	NA
6	AVG	1	19.05.2018	T	T	NA	NA	T	NA	T	T	NA	T	NA	NA
9	Avira	1	19.05.2018	NA	T	T	NA	NA	NA	T	T	NA	T	NA	NA
11	Webroot	1	19.05.2018	NA	T	NA	NA	T	T	T	T	NA	T	NA	NA
7	Norton	1	19.05.2018	T	T	T	NA	T	T	NA	NA	NA	T	NA	NA
10	Bitdefender	1	19.05.2018	T	T	T	NA	T	NA	T	T	NA	T	T	NA
4	IObit	1	19.05.2018	NA	T	NA	NA	T	T	T	T	NA	T	NA	NA
2	DR WEB	1	19.05.2018	T	T	T	T	T	T	NA	T	T	T	NA	T
3	Avast	1	19.05.2018	T	T	NA	NA	T	NA	T	T	T	T	T	NA
14	Avast	2	22.05.2018	T	T	NA	NA	T	NA	T	T	T	T	T	NA
1	Bullguard	1	19.05.2018	T	T	T	NA	T	T	T	NA	NA	T	NA	NA
8	eScan	1	19.05.2018	T	T	NA	NA	T	NA	NA	T	NA	T	NA	NA
12	Comodo	1	19.05.2018	T	T	T	NA	T	T	T	T	T	T	NA	NA
13	Comodo	2	19.05.2018	T	T	T	NA	T	T	T	T	T	T	NA	NA
\.


--
-- Name: antivirus_software Antyviruses_software_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.antivirus_software
    ADD CONSTRAINT "Antyviruses_software_pkey" PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

