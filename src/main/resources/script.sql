create table colors
(
    id          int        not null
        primary key,
    name        varchar(200)       null,
    rgb         varchar(6)       null,
    transparent tinyint(1) null
);

create table inventories
(
    id      int         not null
        primary key,
    version int         null,
    set_num varchar(20) null
);

create table minifigs
(
    fig_num   varchar(20) not null
        primary key,
    name      varchar(256)        null,
    num_parts int         null
);

create table inventory_minifigs
(
    id           int auto_increment
        primary key,
    inventory_id int         null,
    fig_num      varchar(20) null,
    quantity     int         null,
    constraint inventory_minifigs_inventories_id_fk
        foreign key (inventory_id) references inventories (id),
    constraint inventory_minifigs_minifigs_fig_num_fk
        foreign key (fig_num) references minifigs (fig_num)
);

create table part_categories
(
    id   int  not null
        primary key,
    name varchar(200) null
);

create table parts
(
    part_num      varchar(20) not null
        primary key,
    name          varchar(250)        null,
    part_cat_id   int         null,
    part_material varchar(250)        null,
    constraint parts_part_categories_id_fk
        foreign key (part_cat_id) references part_categories (id)
);

create table elements
(
    id         int auto_increment
        primary key,
    element_id varchar(10) null,
    part_num   varchar(20) null,
    color_id   int         null,
    constraint elements_colors_id_fk
        foreign key (color_id) references colors (id),
    constraint elements_parts_part_num_fk
        foreign key (part_num) references parts (part_num)
);

create table inventory_parts
(
    id           int auto_increment
        primary key,
    inventory_id int         null,
    part_num     varchar(20) null,
    color_id     int         null,
    quantity     int         null,
    spare        tinyint(1)  null,
    constraint inventory_parts_colors_id_fk
        foreign key (color_id) references colors (id),
    constraint inventory_parts_inventories_id_fk
        foreign key (inventory_id) references inventories (id),
    constraint inventory_parts_parts_part_num_fk
        foreign key (part_num) references parts (part_num)
);

create table part_relationships
(
    id              int auto_increment
        primary key,
    rel_type        varchar(1)        null,
    child_part_num  varchar(20) null,
    parent_part_num varchar(20) null,
    constraint part_relationships_parent_parts__fk
        foreign key (parent_part_num) references parts (part_num),
    constraint part_relationships_parts_part_num_fk
        foreign key (child_part_num) references parts (part_num)
);

create table themes
(
    id        int  not null
        primary key,
    name      varchar(40) null,
    parent_id int  null
);

create table sets
(
    set_num   varchar(20) not null
        primary key,
    name      varchar(256)        null,
    year      int         null,
    theme_id  int         null,
    num_parts int         null,
    constraint sets_themes_id_fk
        foreign key (theme_id) references themes (id)
);

create table inventory_sets
(
    id           int auto_increment
        primary key,
    inventory_id int         null,
    set_num      varchar(20) null,
    quantity     int         null,
    constraint inventory_sets_inventories_id_fk
        foreign key (inventory_id) references inventories (id),
    constraint inventory_sets_sets_set_num_fk
        foreign key (set_num) references sets (set_num)
);


