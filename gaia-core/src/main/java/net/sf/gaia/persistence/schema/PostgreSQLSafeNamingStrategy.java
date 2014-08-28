package net.sf.gaia.persistence.schema;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author daniel.joppi
 *
 */
public class PostgreSQLSafeNamingStrategy extends SafeNamingStrategy {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(PostgreSQLSafeNamingStrategy.class);
	
	private static final long serialVersionUID = 1L;

	public static final PostgreSQLSafeNamingStrategy INSTANCE = new PostgreSQLSafeNamingStrategy();

	private static final Set<String> keywordSet = new HashSet<String>();
	static
	{
		keywordSet.add("a");
		keywordSet.add("abort");
		keywordSet.add("abs");
		keywordSet.add("absent");
		keywordSet.add("absolute");
		keywordSet.add("access");
		keywordSet.add("according");
		keywordSet.add("action");
		keywordSet.add("ada");
		keywordSet.add("add");
		keywordSet.add("admin");
		keywordSet.add("after");
		keywordSet.add("aggregate");
		keywordSet.add("alias");
		keywordSet.add("all");
		keywordSet.add("allocate");
		keywordSet.add("also");
		keywordSet.add("alter");
		keywordSet.add("always");
		keywordSet.add("analyse");
		keywordSet.add("analyze");
		keywordSet.add("and");
		keywordSet.add("any");
		keywordSet.add("are");
		keywordSet.add("array");
		keywordSet.add("array_agg");
		keywordSet.add("as");
		keywordSet.add("asc");
		keywordSet.add("asensitive");
		keywordSet.add("assertion");
		keywordSet.add("assignment");
		keywordSet.add("asymmetric");
		keywordSet.add("at");
		keywordSet.add("atomic");
		keywordSet.add("attribute");
		keywordSet.add("attributes");
		keywordSet.add("authorization");
		keywordSet.add("avg");
		keywordSet.add("backward");
		keywordSet.add("base64");
		keywordSet.add("before");
		keywordSet.add("begin");
		keywordSet.add("bernoulli");
		keywordSet.add("between");
		keywordSet.add("bigint");
		keywordSet.add("binary");
		keywordSet.add("bit");
		keywordSet.add("bitvar");
		keywordSet.add("bit_length");
		keywordSet.add("blob");
		keywordSet.add("bom");
		keywordSet.add("boolean");
		keywordSet.add("both");
		keywordSet.add("breadth");
		keywordSet.add("by");
		keywordSet.add("c");
		keywordSet.add("cache");
		keywordSet.add("call");
		keywordSet.add("called");
		keywordSet.add("cardinality");
		keywordSet.add("cascade");
		keywordSet.add("cascaded");
		keywordSet.add("case");
		keywordSet.add("cast");
		keywordSet.add("catalog");
		keywordSet.add("catalog_name");
		keywordSet.add("ceil");
		keywordSet.add("ceiling");
		keywordSet.add("chain");
		keywordSet.add("char");
		keywordSet.add("character");
		keywordSet.add("characteristics");
		keywordSet.add("characters");
		keywordSet.add("character_length");
		keywordSet.add("character_set_catalog");
		keywordSet.add("character_set_name");
		keywordSet.add("character_set_schema");
		keywordSet.add("char_length");
		keywordSet.add("check");
		keywordSet.add("checked");
		keywordSet.add("checkpoint");
		keywordSet.add("class");
		keywordSet.add("class_origin");
		keywordSet.add("clob");
		keywordSet.add("close");
		keywordSet.add("cluster");
		keywordSet.add("coalesce");
		keywordSet.add("cobol");
		keywordSet.add("collate");
		keywordSet.add("collation");
		keywordSet.add("collation_catalog");
		keywordSet.add("collation_name");
		keywordSet.add("collation_schema");
		keywordSet.add("collect");
		keywordSet.add("column");
		keywordSet.add("columns");
		keywordSet.add("column_name");
		keywordSet.add("command_function");
		keywordSet.add("command_function_code");
		keywordSet.add("comment");
		keywordSet.add("commit");
		keywordSet.add("committed");
		keywordSet.add("completion");
		keywordSet.add("concurrently");
		keywordSet.add("condition");
		keywordSet.add("condition_number");
		keywordSet.add("configuration");
		keywordSet.add("connect");
		keywordSet.add("connection");
		keywordSet.add("connection_name");
		keywordSet.add("constraint");
		keywordSet.add("constraints");
		keywordSet.add("constraint_catalog");
		keywordSet.add("constraint_name");
		keywordSet.add("constraint_schema");
		keywordSet.add("constructor");
		keywordSet.add("contains");
		keywordSet.add("content");
		keywordSet.add("continue");
		keywordSet.add("conversion");
		keywordSet.add("convert");
		keywordSet.add("copy");
		keywordSet.add("corr");
		keywordSet.add("corresponding");
		keywordSet.add("cost");
		keywordSet.add("count");
		keywordSet.add("covar_pop");
		keywordSet.add("covar_samp");
		keywordSet.add("create");
		keywordSet.add("createdb");
		keywordSet.add("createrole");
		keywordSet.add("createuser");
		keywordSet.add("cross");
		keywordSet.add("csv");
		keywordSet.add("cube");
		keywordSet.add("cume_dist");
		keywordSet.add("current");
		keywordSet.add("current_catalog");
		keywordSet.add("current_date");
		keywordSet.add("current_default_transform_group");
		keywordSet.add("current_path");
		keywordSet.add("current_role");
		keywordSet.add("current_schema");
		keywordSet.add("current_time");
		keywordSet.add("current_timestamp");
		keywordSet.add("current_transform_group_for_type");
		keywordSet.add("current_user");
		keywordSet.add("cursor");
		keywordSet.add("cursor_name");
		keywordSet.add("cycle");
		keywordSet.add("data");
		keywordSet.add("database");
		keywordSet.add("date");
		keywordSet.add("datetime_interval_code");
		keywordSet.add("datetime_interval_precision");
		keywordSet.add("day");
		keywordSet.add("deallocate");
		keywordSet.add("dec");
		keywordSet.add("decimal");
		keywordSet.add("declare");
		keywordSet.add("default");
		keywordSet.add("defaults");
		keywordSet.add("deferrable");
		keywordSet.add("deferred");
		keywordSet.add("defined");
		keywordSet.add("definer");
		keywordSet.add("degree");
		keywordSet.add("delete");
		keywordSet.add("delimiter");
		keywordSet.add("delimiters");
		keywordSet.add("dense_rank");
		keywordSet.add("depth");
		keywordSet.add("deref");
		keywordSet.add("derived");
		keywordSet.add("desc");
		keywordSet.add("describe");
		keywordSet.add("descriptor");
		keywordSet.add("destroy");
		keywordSet.add("destructor");
		keywordSet.add("deterministic");
		keywordSet.add("diagnostics");
		keywordSet.add("dictionary");
		keywordSet.add("disable");
		keywordSet.add("discard");
		keywordSet.add("disconnect");
		keywordSet.add("dispatch");
		keywordSet.add("distinct");
		keywordSet.add("do");
		keywordSet.add("document");
		keywordSet.add("domain");
		keywordSet.add("double");
		keywordSet.add("drop");
		keywordSet.add("dynamic");
		keywordSet.add("dynamic_function");
		keywordSet.add("dynamic_function_code");
		keywordSet.add("each");
		keywordSet.add("element");
		keywordSet.add("else");
		keywordSet.add("empty");
		keywordSet.add("enable");
		keywordSet.add("encoding");
		keywordSet.add("encrypted");
		keywordSet.add("end");
		keywordSet.add("end-exec");
		keywordSet.add("enum");
		keywordSet.add("equals");
		keywordSet.add("escape");
		keywordSet.add("every");
		keywordSet.add("except");
		keywordSet.add("exception");
		keywordSet.add("exclude");
		keywordSet.add("excluding");
		keywordSet.add("exclusive");
		keywordSet.add("exec");
		keywordSet.add("execute");
		keywordSet.add("existing");
		keywordSet.add("exists");
		keywordSet.add("exp");
		keywordSet.add("explain");
		keywordSet.add("external");
		keywordSet.add("extract");
		keywordSet.add("false");
		keywordSet.add("family");
		keywordSet.add("fetch");
		keywordSet.add("filter");
		keywordSet.add("final");
		keywordSet.add("first");
		keywordSet.add("first_value");
		keywordSet.add("flag");
		keywordSet.add("float");
		keywordSet.add("floor");
		keywordSet.add("following");
		keywordSet.add("for");
		keywordSet.add("force");
		keywordSet.add("foreign");
		keywordSet.add("fortran");
		keywordSet.add("forward");
		keywordSet.add("found");
		keywordSet.add("free");
		keywordSet.add("freeze");
		keywordSet.add("from");
		keywordSet.add("full");
		keywordSet.add("function");
		keywordSet.add("fusion");
		keywordSet.add("g");
		keywordSet.add("general");
		keywordSet.add("generated");
		keywordSet.add("get");
		keywordSet.add("global");
		keywordSet.add("go");
		keywordSet.add("goto");
		keywordSet.add("grant");
		keywordSet.add("granted");
		keywordSet.add("greatest");
		keywordSet.add("group");
		keywordSet.add("grouping");
		keywordSet.add("handler");
		keywordSet.add("having");
		keywordSet.add("header");
		keywordSet.add("hex");
		keywordSet.add("hierarchy");
		keywordSet.add("hold");
		keywordSet.add("host");
		keywordSet.add("hour");
		keywordSet.add("id");
		keywordSet.add("identity");
		keywordSet.add("if");
		keywordSet.add("ignore");
		keywordSet.add("ilike");
		keywordSet.add("immediate");
		keywordSet.add("immutable");
		keywordSet.add("implementation");
		keywordSet.add("implicit");
		keywordSet.add("in");
		keywordSet.add("including");
		keywordSet.add("increment");
		keywordSet.add("indent");
		keywordSet.add("index");
		keywordSet.add("indexes");
		keywordSet.add("indicator");
		keywordSet.add("infix");
		keywordSet.add("inherit");
		keywordSet.add("inherits");
		keywordSet.add("initialize");
		keywordSet.add("initially");
		keywordSet.add("inner");
		keywordSet.add("inout");
		keywordSet.add("input");
		keywordSet.add("insensitive");
		keywordSet.add("insert");
		keywordSet.add("instance");
		keywordSet.add("instantiable");
		keywordSet.add("instead");
		keywordSet.add("int");
		keywordSet.add("integer");
		keywordSet.add("intersect");
		keywordSet.add("intersection");
		keywordSet.add("interval");
		keywordSet.add("into");
		keywordSet.add("invoker");
		keywordSet.add("is");
		keywordSet.add("isnull");
		keywordSet.add("isolation");
		keywordSet.add("iterate");
		keywordSet.add("join");
		keywordSet.add("k");
		keywordSet.add("key");
		keywordSet.add("key_member");
		keywordSet.add("key_type");
		keywordSet.add("lag");
		keywordSet.add("lancompiler");
		keywordSet.add("language");
		keywordSet.add("large");
		keywordSet.add("last");
		keywordSet.add("last_value");
		keywordSet.add("lateral");
		keywordSet.add("lc_collate");
		keywordSet.add("lc_ctype");
		keywordSet.add("lead");
		keywordSet.add("leading");
		keywordSet.add("least");
		keywordSet.add("left");
		keywordSet.add("length");
		keywordSet.add("less");
		keywordSet.add("level");
		keywordSet.add("like");
		keywordSet.add("like_regex");
		keywordSet.add("limit");
		keywordSet.add("listen");
		keywordSet.add("ln");
		keywordSet.add("load");
		keywordSet.add("local");
		keywordSet.add("localtime");
		keywordSet.add("localtimestamp");
		keywordSet.add("location");
		keywordSet.add("locator");
		keywordSet.add("lock");
		keywordSet.add("login");
		keywordSet.add("lower");
		keywordSet.add("m");
		keywordSet.add("map");
		keywordSet.add("mapping");
		keywordSet.add("match");
		keywordSet.add("matched");
		keywordSet.add("max");
		keywordSet.add("maxvalue");
		keywordSet.add("max_cardinality");
		keywordSet.add("member");
		keywordSet.add("merge");
		keywordSet.add("message_length");
		keywordSet.add("message_octet_length");
		keywordSet.add("message_text");
		keywordSet.add("method");
		keywordSet.add("min");
		keywordSet.add("minute");
		keywordSet.add("minvalue");
		keywordSet.add("mod");
		keywordSet.add("mode");
		keywordSet.add("modifies");
		keywordSet.add("modify");
		keywordSet.add("module");
		keywordSet.add("month");
		keywordSet.add("more");
		keywordSet.add("move");
		keywordSet.add("multiset");
		keywordSet.add("mumps");
		keywordSet.add("name");
		keywordSet.add("names");
		keywordSet.add("namespace");
		keywordSet.add("national");
		keywordSet.add("natural");
		keywordSet.add("nchar");
		keywordSet.add("nclob");
		keywordSet.add("nesting");
		keywordSet.add("new");
		keywordSet.add("next");
		keywordSet.add("nfc");
		keywordSet.add("nfd");
		keywordSet.add("nfkc");
		keywordSet.add("nfkd");
		keywordSet.add("nil");
		keywordSet.add("no");
		keywordSet.add("nocreatedb");
		keywordSet.add("nocreaterole");
		keywordSet.add("nocreateuser");
		keywordSet.add("noinherit");
		keywordSet.add("nologin");
		keywordSet.add("none");
		keywordSet.add("normalize");
		keywordSet.add("normalized");
		keywordSet.add("nosuperuser");
		keywordSet.add("not");
		keywordSet.add("nothing");
		keywordSet.add("notify");
		keywordSet.add("notnull");
		keywordSet.add("nowait");
		keywordSet.add("nth_value");
		keywordSet.add("ntile");
		keywordSet.add("null");
		keywordSet.add("nullable");
		keywordSet.add("nullif");
		keywordSet.add("nulls");
		keywordSet.add("number");
		keywordSet.add("numeric");
		keywordSet.add("object");
		keywordSet.add("occurrences_regex");
		keywordSet.add("octets");
		keywordSet.add("octet_length");
		keywordSet.add("of");
		keywordSet.add("off");
		keywordSet.add("offset");
		keywordSet.add("oids");
		keywordSet.add("old");
		keywordSet.add("on");
		keywordSet.add("only");
		keywordSet.add("open");
		keywordSet.add("operation");
		keywordSet.add("operator");
		keywordSet.add("option");
		keywordSet.add("options");
		keywordSet.add("or");
		keywordSet.add("order");
		keywordSet.add("ordering");
		keywordSet.add("ordinality");
		keywordSet.add("others");
		keywordSet.add("out");
		keywordSet.add("outer");
		keywordSet.add("output");
		keywordSet.add("over");
		keywordSet.add("overlaps");
		keywordSet.add("overlay");
		keywordSet.add("overriding");
		keywordSet.add("owned");
		keywordSet.add("owner");
		keywordSet.add("p");
		keywordSet.add("pad");
		keywordSet.add("parameter");
		keywordSet.add("parameters");
		keywordSet.add("parameter_mode");
		keywordSet.add("parameter_name");
		keywordSet.add("parameter_ordinal_position");
		keywordSet.add("parameter_specific_catalog");
		keywordSet.add("parameter_specific_name");
		keywordSet.add("parameter_specific_schema");
		keywordSet.add("parser");
		keywordSet.add("partial");
		keywordSet.add("partition");
		keywordSet.add("pascal");
		keywordSet.add("passing");
		keywordSet.add("password");
		keywordSet.add("path");
		keywordSet.add("percentile_cont");
		keywordSet.add("percentile_disc");
		keywordSet.add("percent_rank");
		keywordSet.add("placing");
		keywordSet.add("plans");
		keywordSet.add("pli");
		keywordSet.add("position");
		keywordSet.add("position_regex");
		keywordSet.add("postfix");
		keywordSet.add("power");
		keywordSet.add("preceding");
		keywordSet.add("precision");
		keywordSet.add("prefix");
		keywordSet.add("preorder");
		keywordSet.add("prepare");
		keywordSet.add("prepared");
		keywordSet.add("preserve");
		keywordSet.add("primary");
		keywordSet.add("prior");
		keywordSet.add("privileges");
		keywordSet.add("procedural");
		keywordSet.add("procedure");
		keywordSet.add("public");
		keywordSet.add("quote");
		keywordSet.add("range");
		keywordSet.add("rank");
		keywordSet.add("read");
		keywordSet.add("reads");
		keywordSet.add("real");
		keywordSet.add("reassign");
		keywordSet.add("recheck");
		keywordSet.add("recursive");
		keywordSet.add("ref");
		keywordSet.add("references");
		keywordSet.add("referencing");
		keywordSet.add("regr_avgx");
		keywordSet.add("regr_avgy");
		keywordSet.add("regr_count");
		keywordSet.add("regr_intercept");
		keywordSet.add("regr_r2");
		keywordSet.add("regr_slope");
		keywordSet.add("regr_sxx");
		keywordSet.add("regr_sxy");
		keywordSet.add("regr_syy");
		keywordSet.add("reindex");
		keywordSet.add("relative");
		keywordSet.add("release");
		keywordSet.add("rename");
		keywordSet.add("repeatable");
		keywordSet.add("replace");
		keywordSet.add("replica");
		keywordSet.add("reset");
		keywordSet.add("respect");
		keywordSet.add("restart");
		keywordSet.add("restrict");
		keywordSet.add("result");
		keywordSet.add("return");
		keywordSet.add("returned_cardinality");
		keywordSet.add("returned_length");
		keywordSet.add("returned_octet_length");
		keywordSet.add("returned_sqlstate");
		keywordSet.add("returning");
		keywordSet.add("returns");
		keywordSet.add("revoke");
		keywordSet.add("right");
		keywordSet.add("role");
		keywordSet.add("rollback");
		keywordSet.add("rollup");
		keywordSet.add("routine");
		keywordSet.add("routine_catalog");
		keywordSet.add("routine_name");
		keywordSet.add("routine_schema");
		keywordSet.add("row");
		keywordSet.add("rows");
		keywordSet.add("row_count");
		keywordSet.add("row_number");
		keywordSet.add("rule");
		keywordSet.add("savepoint");
		keywordSet.add("scale");
		keywordSet.add("schema");
		keywordSet.add("schema_name");
		keywordSet.add("scope");
		keywordSet.add("scope_catalog");
		keywordSet.add("scope_name");
		keywordSet.add("scope_schema");
		keywordSet.add("scroll");
		keywordSet.add("search");
		keywordSet.add("second");
		keywordSet.add("section");
		keywordSet.add("security");
		keywordSet.add("select");
		keywordSet.add("self");
		keywordSet.add("sensitive");
		keywordSet.add("sequence");
		keywordSet.add("serializable");
		keywordSet.add("server");
		keywordSet.add("server_name");
		keywordSet.add("session");
		keywordSet.add("session_user");
		keywordSet.add("set");
		keywordSet.add("setof");
		keywordSet.add("sets");
		keywordSet.add("share");
		keywordSet.add("show");
		keywordSet.add("similar");
		keywordSet.add("simple");
		keywordSet.add("size");
		keywordSet.add("smallint");
		keywordSet.add("some");
		keywordSet.add("source");
		keywordSet.add("space");
		keywordSet.add("specific");
		keywordSet.add("specifictype");
		keywordSet.add("specific_name");
		keywordSet.add("sql");
		keywordSet.add("sqlcode");
		keywordSet.add("sqlerror");
		keywordSet.add("sqlexception");
		keywordSet.add("sqlstate");
		keywordSet.add("sqlwarning");
		keywordSet.add("sqrt");
		keywordSet.add("stable");
		keywordSet.add("standalone");
		keywordSet.add("start");
		keywordSet.add("state");
		keywordSet.add("statement");
		keywordSet.add("static");
		keywordSet.add("statistics");
		keywordSet.add("stddev_pop");
		keywordSet.add("stddev_samp");
		keywordSet.add("stdin");
		keywordSet.add("stdout");
		keywordSet.add("storage");
		keywordSet.add("strict");
		keywordSet.add("strip");
		keywordSet.add("structure");
		keywordSet.add("style");
		keywordSet.add("subclass_origin");
		keywordSet.add("sublist");
		keywordSet.add("submultiset");
		keywordSet.add("substring");
		keywordSet.add("substring_regex");
		keywordSet.add("sum");
		keywordSet.add("superuser");
		keywordSet.add("symmetric");
		keywordSet.add("sysid");
		keywordSet.add("system");
		keywordSet.add("system_user");
		keywordSet.add("t");
		keywordSet.add("table");
		keywordSet.add("tablesample");
		keywordSet.add("tablespace");
		keywordSet.add("table_name");
		keywordSet.add("temp");
		keywordSet.add("template");
		keywordSet.add("temporary");
		keywordSet.add("terminate");
		keywordSet.add("text");
		keywordSet.add("than");
		keywordSet.add("then");
		keywordSet.add("ties");
		keywordSet.add("time");
		keywordSet.add("timestamp");
		keywordSet.add("timezone_hour");
		keywordSet.add("timezone_minute");
		keywordSet.add("to");
		keywordSet.add("top_level_count");
		keywordSet.add("trailing");
		keywordSet.add("transaction");
		keywordSet.add("transactions_committed");
		keywordSet.add("transactions_rolled_back");
		keywordSet.add("transaction_active");
		keywordSet.add("transform");
		keywordSet.add("transforms");
		keywordSet.add("translate");
		keywordSet.add("translate_regex");
		keywordSet.add("translation");
		keywordSet.add("treat");
		keywordSet.add("trigger");
		keywordSet.add("trigger_catalog");
		keywordSet.add("trigger_name");
		keywordSet.add("trigger_schema");
		keywordSet.add("trim");
		keywordSet.add("trim_array");
		keywordSet.add("true");
		keywordSet.add("truncate");
		keywordSet.add("trusted");
		keywordSet.add("type");
		keywordSet.add("uescape");
		keywordSet.add("unbounded");
		keywordSet.add("uncommitted");
		keywordSet.add("under");
		keywordSet.add("unencrypted");
		keywordSet.add("union");
		keywordSet.add("unique");
		keywordSet.add("unknown");
		keywordSet.add("unlisten");
		keywordSet.add("unnamed");
		keywordSet.add("unnest");
		keywordSet.add("until");
		keywordSet.add("untyped");
		keywordSet.add("update");
		keywordSet.add("upper");
		keywordSet.add("uri");
		keywordSet.add("usage");
		keywordSet.add("user");
		keywordSet.add("user_defined_type_catalog");
		keywordSet.add("user_defined_type_code");
		keywordSet.add("user_defined_type_name");
		keywordSet.add("user_defined_type_schema");
		keywordSet.add("using");
		keywordSet.add("vacuum");
		keywordSet.add("valid");
		keywordSet.add("validator");
		keywordSet.add("value");
		keywordSet.add("values");
		keywordSet.add("varbinary");
		keywordSet.add("varchar");
		keywordSet.add("variable");
		keywordSet.add("variadic");
		keywordSet.add("varying");
		keywordSet.add("var_pop");
		keywordSet.add("var_samp");
		keywordSet.add("verbose");
		keywordSet.add("version");
		keywordSet.add("view");
		keywordSet.add("volatile");
		keywordSet.add("when");
		keywordSet.add("whenever");
		keywordSet.add("where");
		keywordSet.add("whitespace");
		keywordSet.add("width_bucket");
		keywordSet.add("window");
		keywordSet.add("with");
		keywordSet.add("within");
		keywordSet.add("without");
		keywordSet.add("work");
		keywordSet.add("wrapper");
		keywordSet.add("write");
		keywordSet.add("xml");
		keywordSet.add("xmlagg");
		keywordSet.add("xmlattributes");
		keywordSet.add("xmlbinary");
		keywordSet.add("xmlcast");
		keywordSet.add("xmlcomment");
		keywordSet.add("xmlconcat");
		keywordSet.add("xmldeclaration");
		keywordSet.add("xmldocument");
		keywordSet.add("xmlelement");
		keywordSet.add("xmlexists");
		keywordSet.add("xmlforest");
		keywordSet.add("xmliterate");
		keywordSet.add("xmlnamespaces");
		keywordSet.add("xmlparse");
		keywordSet.add("xmlpi");
		keywordSet.add("xmlquery");
		keywordSet.add("xmlroot");
		keywordSet.add("xmlschema");
		keywordSet.add("xmlserialize");
		keywordSet.add("xmltable");
		keywordSet.add("xmltext");
		keywordSet.add("xmlvalidate");
		keywordSet.add("year");
		keywordSet.add("yes");
		keywordSet.add("zone");
	}

	@Override
	protected Set<String> getKeywordSet()
	{
		return keywordSet;
	}
}