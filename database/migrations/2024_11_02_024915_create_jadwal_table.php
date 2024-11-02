<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('jadwal', function (Blueprint $table) {
            $table->id();
            $table->integer("armada_id");
            $table->string("hari");
            $table->integer("tanggal");
            $table->string("bulan");
            $table->integer("tahun");
            $table->date("waktu_keberagkatan");
            $table->string("lokasi_keberangkatan");
            $table->string("tujuan");
            $table->integer("harga_tiket");
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('jadwal');
    }
};
