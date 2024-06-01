PGDMP                         |            postgres    15.4    15.4 J    X           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            Y           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            Z           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            [           1262    5    postgres    DATABASE        CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE postgres;
                postgres    false            \           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                   postgres    false    3419                        3079    16384 	   adminpack 	   EXTENSION     A   CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;
    DROP EXTENSION adminpack;
                   false            ]           0    0    EXTENSION adminpack    COMMENT     M   COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';
                        false    2            `           1247    205164    tipo_evento    TYPE     H   CREATE TYPE public.tipo_evento AS ENUM (
    'FISICO',
    'VIRTUAL'
);
    DROP TYPE public.tipo_evento;
       public          postgres    false            �            1259    205293    evento_participantes    TABLE     n   CREATE TABLE public.evento_participantes (
    evento_id integer NOT NULL,
    usuario_id integer NOT NULL
);
 (   DROP TABLE public.evento_participantes;
       public         heap    postgres    false            �            1259    205280    eventos    TABLE     ;  CREATE TABLE public.eventos (
    id integer NOT NULL,
    titulo character varying(255) NOT NULL,
    descricao text,
    localizacao character varying(255),
    data_inicio timestamp without time zone,
    data_fim timestamp without time zone,
    tipo_evento integer NOT NULL,
    usuario_id integer NOT NULL
);
    DROP TABLE public.eventos;
       public         heap    postgres    false            �            1259    205279    eventos_id_seq    SEQUENCE     �   CREATE SEQUENCE public.eventos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.eventos_id_seq;
       public          postgres    false    222            ^           0    0    eventos_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.eventos_id_seq OWNED BY public.eventos.id;
          public          postgres    false    221            �            1259    16412    flyway_schema_history    TABLE     �  CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);
 )   DROP TABLE public.flyway_schema_history;
       public         heap    postgres    false            �            1259    205366 
   frequencia    TABLE     �   CREATE TABLE public.frequencia (
    id bigint NOT NULL,
    data_registro timestamp without time zone,
    evento_id bigint,
    usuario_id bigint,
    data_hora_registro timestamp without time zone NOT NULL,
    aluno_id bigint NOT NULL
);
    DROP TABLE public.frequencia;
       public         heap    postgres    false            �            1259    205365    frequencia_id_seq    SEQUENCE     z   CREATE SEQUENCE public.frequencia_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.frequencia_id_seq;
       public          postgres    false    225            _           0    0    frequencia_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.frequencia_id_seq OWNED BY public.frequencia.id;
          public          postgres    false    224            �            1259    205100    roles    TABLE     `   CREATE TABLE public.roles (
    id integer NOT NULL,
    nome character varying(20) NOT NULL
);
    DROP TABLE public.roles;
       public         heap    postgres    false            �            1259    205099    roles_id_seq    SEQUENCE     �   CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.roles_id_seq;
       public          postgres    false    219            `           0    0    roles_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;
          public          postgres    false    218            �            1259    205373    solucao    TABLE        CREATE TABLE public.solucao (
    id bigint NOT NULL,
    caminho_arquivo character varying(255) NOT NULL,
    comentario_avaliacao text,
    data_submissao timestamp without time zone NOT NULL,
    nota_avaliacao integer,
    aluno_id bigint NOT NULL,
    trabalho_id bigint NOT NULL
);
    DROP TABLE public.solucao;
       public         heap    postgres    false            �            1259    205372    solucao_id_seq    SEQUENCE     w   CREATE SEQUENCE public.solucao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.solucao_id_seq;
       public          postgres    false    227            a           0    0    solucao_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.solucao_id_seq OWNED BY public.solucao.id;
          public          postgres    false    226            �            1259    205382    trabalho    TABLE     �   CREATE TABLE public.trabalho (
    id bigint NOT NULL,
    descricao text,
    titulo character varying(255) NOT NULL,
    autor_id bigint NOT NULL
);
    DROP TABLE public.trabalho;
       public         heap    postgres    false            �            1259    205381    trabalho_id_seq    SEQUENCE     x   CREATE SEQUENCE public.trabalho_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.trabalho_id_seq;
       public          postgres    false    229            b           0    0    trabalho_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.trabalho_id_seq OWNED BY public.trabalho.id;
          public          postgres    false    228            �            1259    205108 
   user_roles    TABLE     ]   CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);
    DROP TABLE public.user_roles;
       public         heap    postgres    false            �            1259    205089    usuario    TABLE     �   CREATE TABLE public.usuario (
    id integer NOT NULL,
    nome_de_usuario character varying(50) NOT NULL,
    senha character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    nome character varying(100) NOT NULL
);
    DROP TABLE public.usuario;
       public         heap    postgres    false            �            1259    205088    usuario_id_seq    SEQUENCE     �   CREATE SEQUENCE public.usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.usuario_id_seq;
       public          postgres    false    217            c           0    0    usuario_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;
          public          postgres    false    216            �           2604    205283 
   eventos id    DEFAULT     h   ALTER TABLE ONLY public.eventos ALTER COLUMN id SET DEFAULT nextval('public.eventos_id_seq'::regclass);
 9   ALTER TABLE public.eventos ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    222    221    222            �           2604    205369    frequencia id    DEFAULT     n   ALTER TABLE ONLY public.frequencia ALTER COLUMN id SET DEFAULT nextval('public.frequencia_id_seq'::regclass);
 <   ALTER TABLE public.frequencia ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    224    225    225            �           2604    205103    roles id    DEFAULT     d   ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);
 7   ALTER TABLE public.roles ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    219    219            �           2604    205376 
   solucao id    DEFAULT     h   ALTER TABLE ONLY public.solucao ALTER COLUMN id SET DEFAULT nextval('public.solucao_id_seq'::regclass);
 9   ALTER TABLE public.solucao ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    226    227    227            �           2604    205385    trabalho id    DEFAULT     j   ALTER TABLE ONLY public.trabalho ALTER COLUMN id SET DEFAULT nextval('public.trabalho_id_seq'::regclass);
 :   ALTER TABLE public.trabalho ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    229    228    229            �           2604    205092 
   usuario id    DEFAULT     h   ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);
 9   ALTER TABLE public.usuario ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    217    217            O          0    205293    evento_participantes 
   TABLE DATA                 public          postgres    false    223   jT       N          0    205280    eventos 
   TABLE DATA                 public          postgres    false    222   �T       G          0    16412    flyway_schema_history 
   TABLE DATA                 public          postgres    false    215   �U       Q          0    205366 
   frequencia 
   TABLE DATA                 public          postgres    false    225   V       K          0    205100    roles 
   TABLE DATA                 public          postgres    false    219   �V       S          0    205373    solucao 
   TABLE DATA                 public          postgres    false    227   HW       U          0    205382    trabalho 
   TABLE DATA                 public          postgres    false    229   �X       L          0    205108 
   user_roles 
   TABLE DATA                 public          postgres    false    220   �Y       I          0    205089    usuario 
   TABLE DATA                 public          postgres    false    217   �Y       d           0    0    eventos_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.eventos_id_seq', 4, true);
          public          postgres    false    221            e           0    0    frequencia_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.frequencia_id_seq', 14, true);
          public          postgres    false    224            f           0    0    roles_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.roles_id_seq', 6, true);
          public          postgres    false    218            g           0    0    solucao_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.solucao_id_seq', 4, true);
          public          postgres    false    226            h           0    0    trabalho_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.trabalho_id_seq', 3, true);
          public          postgres    false    228            i           0    0    usuario_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.usuario_id_seq', 10, true);
          public          postgres    false    216            �           2606    205297 .   evento_participantes evento_participantes_pkey 
   CONSTRAINT        ALTER TABLE ONLY public.evento_participantes
    ADD CONSTRAINT evento_participantes_pkey PRIMARY KEY (evento_id, usuario_id);
 X   ALTER TABLE ONLY public.evento_participantes DROP CONSTRAINT evento_participantes_pkey;
       public            postgres    false    223    223            �           2606    205287    eventos eventos_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.eventos
    ADD CONSTRAINT eventos_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.eventos DROP CONSTRAINT eventos_pkey;
       public            postgres    false    222            �           2606    16419 .   flyway_schema_history flyway_schema_history_pk 
   CONSTRAINT     x   ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);
 X   ALTER TABLE ONLY public.flyway_schema_history DROP CONSTRAINT flyway_schema_history_pk;
       public            postgres    false    215            �           2606    205371    frequencia frequencia_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.frequencia
    ADD CONSTRAINT frequencia_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.frequencia DROP CONSTRAINT frequencia_pkey;
       public            postgres    false    225            �           2606    205107    roles roles_nome_key 
   CONSTRAINT     O   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_nome_key UNIQUE (nome);
 >   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_nome_key;
       public            postgres    false    219            �           2606    205105    roles roles_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_pkey;
       public            postgres    false    219            �           2606    205380    solucao solucao_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.solucao
    ADD CONSTRAINT solucao_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.solucao DROP CONSTRAINT solucao_pkey;
       public            postgres    false    227            �           2606    205389    trabalho trabalho_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.trabalho
    ADD CONSTRAINT trabalho_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.trabalho DROP CONSTRAINT trabalho_pkey;
       public            postgres    false    229            �           2606    205112    user_roles user_roles_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);
 D   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_pkey;
       public            postgres    false    220    220            �           2606    205098    usuario usuario_email_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);
 C   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_email_key;
       public            postgres    false    217            �           2606    205096 #   usuario usuario_nome_de_usuario_key 
   CONSTRAINT     i   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_nome_de_usuario_key UNIQUE (nome_de_usuario);
 M   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_nome_de_usuario_key;
       public            postgres    false    217            �           2606    205094    usuario usuario_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public            postgres    false    217            �           1259    16420    flyway_schema_history_s_idx    INDEX     `   CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);
 /   DROP INDEX public.flyway_schema_history_s_idx;
       public            postgres    false    215            �           2606    205298 8   evento_participantes evento_participantes_evento_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.evento_participantes
    ADD CONSTRAINT evento_participantes_evento_id_fkey FOREIGN KEY (evento_id) REFERENCES public.eventos(id);
 b   ALTER TABLE ONLY public.evento_participantes DROP CONSTRAINT evento_participantes_evento_id_fkey;
       public          postgres    false    223    222    3237            �           2606    205303 9   evento_participantes evento_participantes_usuario_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.evento_participantes
    ADD CONSTRAINT evento_participantes_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);
 c   ALTER TABLE ONLY public.evento_participantes DROP CONSTRAINT evento_participantes_usuario_id_fkey;
       public          postgres    false    223    217    3229            �           2606    205288    eventos eventos_usuario_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.eventos
    ADD CONSTRAINT eventos_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);
 I   ALTER TABLE ONLY public.eventos DROP CONSTRAINT eventos_usuario_id_fkey;
       public          postgres    false    217    3229    222            �           2606    205400 #   solucao fk382v06py2v6amke6efnx4tgbj    FK CONSTRAINT     �   ALTER TABLE ONLY public.solucao
    ADD CONSTRAINT fk382v06py2v6amke6efnx4tgbj FOREIGN KEY (aluno_id) REFERENCES public.usuario(id);
 M   ALTER TABLE ONLY public.solucao DROP CONSTRAINT fk382v06py2v6amke6efnx4tgbj;
       public          postgres    false    3229    227    217            �           2606    205410 $   trabalho fk4t8rx9yb7vw304j1xrsao7hjl    FK CONSTRAINT     �   ALTER TABLE ONLY public.trabalho
    ADD CONSTRAINT fk4t8rx9yb7vw304j1xrsao7hjl FOREIGN KEY (autor_id) REFERENCES public.usuario(id);
 N   ALTER TABLE ONLY public.trabalho DROP CONSTRAINT fk4t8rx9yb7vw304j1xrsao7hjl;
       public          postgres    false    3229    217    229            �           2606    205118 &   user_roles fk6hdcxig28sjux082ekdae5wnj    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fk6hdcxig28sjux082ekdae5wnj FOREIGN KEY (user_id) REFERENCES public.usuario(id);
 P   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fk6hdcxig28sjux082ekdae5wnj;
       public          postgres    false    217    220    3229            �           2606    205405 #   solucao fk7l6fh0gj68s7wtbamworomr6n    FK CONSTRAINT     �   ALTER TABLE ONLY public.solucao
    ADD CONSTRAINT fk7l6fh0gj68s7wtbamworomr6n FOREIGN KEY (trabalho_id) REFERENCES public.trabalho(id);
 M   ALTER TABLE ONLY public.solucao DROP CONSTRAINT fk7l6fh0gj68s7wtbamworomr6n;
       public          postgres    false    3245    229    227            �           2606    205416 &   frequencia fkfyu52cphm8glixkadtx5lx0kq    FK CONSTRAINT     �   ALTER TABLE ONLY public.frequencia
    ADD CONSTRAINT fkfyu52cphm8glixkadtx5lx0kq FOREIGN KEY (aluno_id) REFERENCES public.usuario(id);
 P   ALTER TABLE ONLY public.frequencia DROP CONSTRAINT fkfyu52cphm8glixkadtx5lx0kq;
       public          postgres    false    225    3229    217            �           2606    205395 &   frequencia fkgnqq94e7qxivug3chwsr2h0a6    FK CONSTRAINT     �   ALTER TABLE ONLY public.frequencia
    ADD CONSTRAINT fkgnqq94e7qxivug3chwsr2h0a6 FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);
 P   ALTER TABLE ONLY public.frequencia DROP CONSTRAINT fkgnqq94e7qxivug3chwsr2h0a6;
       public          postgres    false    3229    225    217            �           2606    205390 &   frequencia fkgt7f439w1uuebioiu1seo0ayx    FK CONSTRAINT     �   ALTER TABLE ONLY public.frequencia
    ADD CONSTRAINT fkgt7f439w1uuebioiu1seo0ayx FOREIGN KEY (evento_id) REFERENCES public.eventos(id);
 P   ALTER TABLE ONLY public.frequencia DROP CONSTRAINT fkgt7f439w1uuebioiu1seo0ayx;
       public          postgres    false    222    225    3237            �           2606    205113 &   user_roles fkh8ciramu9cc9q3qcqiv4ue8a6    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);
 P   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6;
       public          postgres    false    220    219    3233            O   p   x���v
Q���W((M��L�K-K�+ɏ/H,*�L�,H�+I-VЀ�f��(��&e�ؚ
a�>���
�:
��
�~
��~n>��!
.�
~�!�~��\�Գ�� �5\\ �WAI      N   �   x���J�@��y�����lbU��X]Xw%�x�$��5[�]��h����� �A���?3���P.���K{���,����f�9�!X�N��nll�o�i�ƃ�����<��Nk{S
��Nhjl�*�0�C���x��]�J؈'x.D�I)
�B��Ϧ)�����A��S��3D���*���&$YE�*"1��B|���/l�^��>��nJ�>�|���6�'���C���P�YBY���'\F�      G   
   x���          Q   �   x�M�A�0�ỿ⻩0��ʴS��@6H�*KWDk�~X�^x^ʊ�Te%���t��Z>��%�Q-�VL����I�K��P�bF#�������gu�ͫ� ��s�v��V^�G�b���'ђD�� v�3H9��4-aǁ��H�acY�$7�      K   x   x���v
Q���W((M��L�+��I-V��L�Q���M�Ts�	uV�0�QP��q��Q�T��Sp��s��tQp�W�����s���$�LS����.�A�!��2�f�����C�� �H�      S   l  x��SMO�0��W�6&�*I�u)��6��Z���Nn�AP�hW~?.�	����ar�ߋ���D�f�m!J�)�C^e�M9(l��hV�~nؽ歡DS�	;C�7,��'<�C^��㺡���P7������1��>�w�\q��B�؍��j���xX=�ki(�[}�붩�%,����+��tA]��1U�v{{�"�p����q`,p��ᶔ��P�Y���	ܥ�:�L!I�Qr3�.�"AJ�]�U���,�e�(އf��&�9���O�G����KB<����\��͏�Ӯ`����Q.܅��>��d�_es��!���8?sƝp.]�󫝘~W��m.����P=����L       U   �   x�͎�
�0E�~���B����U%]%6DS��q�C�cV��&��ӽ\�ChU�9�4�^c=�V�>�P& ��-��$�ѷ�O�zcw(c���MQA�&�/��w7���"��:6ڄ=���b��q�B��$��e|E�r��N��p���8uW�N=�U�z�>�!N�F      L   b   x���v
Q���W((M��L�+-N-�/��I-V� �3St@| CS!��'�5XA�RG�DS��O�������9D��_��?����ݚ˓\c��� 74�      I     x����n�@F�<�]��	1�ğ��E%����0�Qa��(�u�(}�Jl��\uwsO�͝� \`8���Ҷ,%�C�e<�댮����hN��[��C�IuO$y�B���a���7���s�4���P̑�bc��w�S�:�^��7��$<�#�_��-!(Ĵ^6����F�_�0�w
j0�F#�0Āp0q��Yq����on�#��?���%��0ux�,�$�!��Qg�+���h�e4M6�(�}4[?����З����(OQ�ί�?     